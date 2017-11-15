package com.anu.developers3k.shareapp.models;

import android.graphics.drawable.Drawable;
import java.util.ArrayList;

/**
 * Created by anuraj on 15/11/17.
 */

public class AppDetails {
  public String name;
  public Drawable icon;
  public String packageName;
  public ArrayList<String> grantedPermissionList = new ArrayList<>();
  public final ArrayList<String> deniedPermissionList = new ArrayList<>();
}
