/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hengyi.baseandroidcore.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/** Junk drawer of utility methods. */
public class DiskLruCacheUtils {
  public static  Charset US_ASCII = Charset.forName("US-ASCII");
  public static Charset UTF_8 = Charset.forName("UTF-8");

  private DiskLruCacheUtils() {
  }

  public static String readFully(Reader reader) throws IOException {
    try {
      StringWriter writer = new StringWriter();
      char[] buffer = new char[1024];
      int count;
      while ((count = reader.read(buffer)) != -1) {
        writer.write(buffer, 0, count);
      }
      return writer.toString();
    } finally {
      reader.close();
    }
  }

  /**
   * Deletes the contents of {@code dir}. Throws an IOException if any file
   * could not be deleted, or if {@code dir} is not a readable directory.
   */
  public static void deleteContents(File dir) throws IOException {
    File[] files = dir.listFiles();
    if (files == null) {
      throw new IOException("not a readable directory: " + dir);
    }
    for (File file : files) {
      if (file.isDirectory()) {
        deleteContents(file);
      }
      if (!file.delete()) {
        throw new IOException("failed to delete file: " + file);
      }
    }
  }

  public static void closeQuietly(/*Auto*/Closeable closeable) {
    if (closeable != null) {
      try {
        closeable.close();
      } catch (RuntimeException rethrown) {
        throw rethrown;
      } catch (Exception ignored) {
      }
    }
  }

  public static String hashKeyForDisk(String key)
  {
    String cacheKey;
    try
    {
      final MessageDigest mDigest = MessageDigest.getInstance("MD5");
      mDigest.update(key.getBytes());
      cacheKey = bytesToHexString(mDigest.digest());
    } catch (NoSuchAlgorithmException e)
    {
      cacheKey = String.valueOf(key.hashCode());
    }
    return cacheKey;
  }

  public static String bytesToHexString(byte[] bytes)
  {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < bytes.length; i++)
    {
      String hex = Integer.toHexString(0xFF & bytes[i]);
      if (hex.length() == 1)
      {
        sb.append('0');
      }
      sb.append(hex);
    }
    return sb.toString();
  }

  public static byte[] bitmap2Bytes(Bitmap bm)
  {
    if (bm == null)
    {
      return null;
    }
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
    return baos.toByteArray();
  }

  public static Bitmap bytes2Bitmap(byte[] bytes)
  {
    return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
  }


  /**
   * Drawable → Bitmap
   */
  public static Bitmap drawable2Bitmap(Drawable drawable)
  {
    if (drawable == null)
    {
      return null;
    }
    // 取 drawable 的长宽
    int w = drawable.getIntrinsicWidth();
    int h = drawable.getIntrinsicHeight();
    // 取 drawable 的颜色格式
    Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
    // 建立对应 bitmap
    Bitmap bitmap = Bitmap.createBitmap(w, h, config);
    // 建立对应 bitmap 的画布
    Canvas canvas = new Canvas(bitmap);
    drawable.setBounds(0, 0, w, h);
    // 把 drawable 内容画到画布中
    drawable.draw(canvas);
    return bitmap;
  }

  /*
   * Bitmap → Drawable
   */
  @SuppressWarnings("deprecation")
  public static Drawable bitmap2Drawable(Bitmap bm)
  {
    if (bm == null)
    {
      return null;
    }
    BitmapDrawable bd = new BitmapDrawable(bm);
    bd.setTargetDensity(bm.getDensity());
    return new BitmapDrawable(bm);
  }

}
