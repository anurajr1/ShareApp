package com.anu.developers3k.shareapp.adapter;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

public class SaveApp {
	private Context context;
	
	public SaveApp(Context context) {
		this.context = context;
	}

	public String extractWithoutRoot(ApplicationInfo info) throws Exception {
		File src = new File(info.sourceDir);
		File dst;

		 dst =new File(Environment.getExternalStorageDirectory()+ "/ShareApp/"+ info.packageName + ".apk");

		dst = buildDstPath(dst);
		try {
			copy(src, dst);
		} catch (IOException ex) {
			throw new Exception(ex.getMessage());
		}
		if (!dst.exists()) {
			throw new Exception("cannot extract file [no root]");
		}
		return dst.toString();
	}

	public String extractWithRoot(ApplicationInfo info) throws Exception {
		File src = new File(info.sourceDir);
		String root = Environment.getExternalStorageDirectory()+ "/ShareApp/"+ info.packageName + ".apk";
		File dst = buildDstPath(new File(root));

		Process p = null;
		StringBuilder err = new StringBuilder();
		try {
			p = Runtime.getRuntime().exec("su -c cat " + src.getAbsolutePath() + " > " + dst.getAbsolutePath());
			p.waitFor();

			if (p.exitValue() != 0) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
				String line = "";
				while ((line = reader.readLine()) != null) {
					err.append(line);
					err.append("\n");
				}

				throw new Exception(err.toString());
			}
		} catch (IOException e) {
			throw new Exception(e.getMessage());
		} catch (InterruptedException e) {
			throw new Exception(e.getMessage());
		} finally {
			if (p != null) {
				try {
					p.destroy();
				} catch (Exception e) {
				}
			}
		}

		if (!dst.exists()) {
			throw new Exception("cannot exctract file [root]");
		}

		return dst.getAbsolutePath();
	}

	/**
	 * Extract APK using MediaStore API (Android 11+ compatible)
	 * Saves to Downloads folder
	 */
	public String extractWithMediaStore(ApplicationInfo info) throws Exception {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) { // Android 10+
			return extractWithMediaStoreAPI(info);
		} else {
			// Fallback to legacy method for older Android versions
			return extractWithoutRoot(info);
		}
	}
	
	private String extractWithMediaStoreAPI(ApplicationInfo info) throws Exception {
		ContentResolver resolver = context.getContentResolver();
		ContentValues contentValues = new ContentValues();
		
		String fileName = info.packageName + ".apk";
		String mimeType = "application/vnd.android.package-archive";
		
		contentValues.put(MediaStore.Downloads.DISPLAY_NAME, fileName);
		contentValues.put(MediaStore.Downloads.MIME_TYPE, mimeType);
		contentValues.put(MediaStore.Downloads.IS_PENDING, 1);
		
		Uri collection = MediaStore.Downloads.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
		Uri itemUri = resolver.insert(collection, contentValues);
		
		if (itemUri == null) {
			throw new Exception("Failed to create MediaStore entry");
		}
		
		try (OutputStream outputStream = resolver.openOutputStream(itemUri);
			 FileInputStream inputStream = new FileInputStream(info.sourceDir)) {
			
			if (outputStream == null) {
				throw new Exception("Failed to open output stream");
			}
			
			byte[] buffer = new byte[8192];
			int bytesRead;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
			outputStream.flush();
			
		} catch (Exception e) {
			// Clean up on failure
			resolver.delete(itemUri, null, null);
			throw new Exception("Failed to save APK: " + e.getMessage());
		}
		
		// Mark as completed
		contentValues.clear();
		contentValues.put(MediaStore.Downloads.IS_PENDING, 0);
		resolver.update(itemUri, contentValues, null, null);
		
		return itemUri.toString();
	}

	private void copy(File src, File dst) throws IOException {
		FileInputStream inStream = new FileInputStream(src);
		FileOutputStream outStream = new FileOutputStream(dst);
		FileChannel inChannel = inStream.getChannel();
		FileChannel outChannel = outStream.getChannel();
		inChannel.transferTo(0, inChannel.size(), outChannel);
		inStream.close();
		outStream.close();
	}

	private File buildDstPath(File path) throws IOException {
		if ((!path.getParentFile().exists() && !path.getParentFile().mkdirs()) || !path.getParentFile().isDirectory()) {
			throw new IOException("Cannot create directory: " + path.getParentFile().getAbsolutePath());
		}
		if (!path.exists()) return path;

		File dst = path;
		String fname = path.getName();
		int index = fname.lastIndexOf(".");
		String ext = fname.substring(index);
		String name = fname.substring(0, index);

		for (int i = 0; dst.exists(); i++) {
			dst = new File(path.getParentFile(), name + "-" + String.valueOf(i) + ext);
		}

		return dst;
	}
}