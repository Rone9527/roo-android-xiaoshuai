package com.roo.core.utils;

import android.app.ActivityManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;

import com.roo.core.app.GlobalContext;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by wanglei on 2016/11/28.
 */

public class Kits {

    public static class Package {
        /**
         * 获取版本号
         *
         * @param context
         * @return
         */
        public static int getVersionCode(Context context) {
            PackageManager pManager = context.getPackageManager();
            PackageInfo packageInfo = null;
            try {
                packageInfo = pManager.getPackageInfo(context.getPackageName(), 0);

            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            return packageInfo.versionCode;
        }

        /**
         * 获取当前版本
         *
         * @param context
         * @return
         */
        public static String getVersionName(Context context) {
            PackageManager pManager = context.getPackageManager();
            try {
                PackageInfo packageInfo = pManager.getPackageInfo(context.getPackageName(), 0);
                return packageInfo.versionName;
            } catch (PackageManager.NameNotFoundException e) {
                return "";
            }
        }

        /**
         * 判断是否是系统App
         *
         * @param context
         * @param packageName 包名
         * @return
         */
        public static boolean isSystemApplication(Context context, String packageName) {
            if (context == null) {
                return false;
            }
            PackageManager packageManager = context.getPackageManager();
            if (packageManager == null || packageName == null || packageName.length() == 0) {
                return false;
            }

            try {
                ApplicationInfo app = packageManager.getApplicationInfo(packageName, 0);
                return (app != null && (app.flags & ApplicationInfo.FLAG_SYSTEM) > 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            return false;
        }


        /**
         * 获取到栈顶应用程序的包名
         */
        public static String getTopActivty(Context context) {
            //android5.1以上获取方式
            String topPackageName = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                UsageStatsManager mUsageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
                long time = System.currentTimeMillis();
                //查询时间设置，时间到了后面不在查询。
                List<UsageStats> stats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 1500, time);
                if (stats != null) {
                    SortedMap<Long, UsageStats> mySortedMap = new TreeMap<>();
                    for (UsageStats usageStats : stats) {
                        mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
                    }
                    if (!mySortedMap.isEmpty()) {
                        topPackageName = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
                        LogManage.i(">>getTopActivty()>>" + "topPackageName = [" + topPackageName + "]");
                    }
                }
            } else {//android5.0以下获取方式
                ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
                List<ActivityManager.RunningTaskInfo> tasks = activityManager.getRunningTasks(1);
                if (tasks == null || tasks.isEmpty()) {
                    return null;
                }
                ActivityManager.RunningTaskInfo taskInfo = tasks.get(0);
                topPackageName = taskInfo.topActivity.getPackageName();

            }
            return topPackageName;
        }

        /**
         * 判断某个包名是否运行在顶层
         */
        public static Boolean isTopActivity(Context context, String packageName) {
            if (Empty.check(packageName)) {
                return false;
            }
            //android5.0以上获取方式
            String topPackageName = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                UsageStatsManager mUsageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
                long time = System.currentTimeMillis();
                //查询时间设置，时间到了后面不在查询。
                List<UsageStats> stats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 1500, time);
                if (stats != null) {
                    SortedMap<Long, UsageStats> mySortedMap = new TreeMap<>();
                    for (UsageStats usageStats : stats) {
                        mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
                    }
                    if (!mySortedMap.isEmpty()) {
                        topPackageName = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
                        LogManage.i(">>isTopActivity()>>" + "topPackageName = [" + topPackageName + "]");
                    }
                }
                return packageName.equals(topPackageName);
            }
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
            if (Empty.check(tasksInfo)) {
                return false;
            }
            try {
                return packageName.equals(tasksInfo.get(0).topActivity.getPackageName());
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        /**
         * 获取Meta-Data
         *
         * @param context
         * @param key
         * @return
         */
        public static String getAppMetaData(Context context, String key) {
            if (context == null || TextUtils.isEmpty(key)) {
                return null;
            }
            String resultData = null;
            try {
                PackageManager packageManager = context.getPackageManager();
                if (packageManager != null) {
                    ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                    if (applicationInfo != null) {
                        if (applicationInfo.metaData != null) {
                            resultData = applicationInfo.metaData.getString(key);
                        }
                    }

                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            return resultData;
        }

        /**
         * 获取当前进程名称
         *
         * @return processName
         */
        public static String getCurrentProcessName(Context context) {
            int currentProcessId = android.os.Process.myPid();
            ActivityManager activityManager = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo runningAppProcess : runningAppProcesses) {
                if (runningAppProcess.pid == currentProcessId) {
                    return runningAppProcess.processName;
                }
            }
            return "";
        }

        /**
         * 判断当前应用是否运行在后台
         *
         * @param context
         * @return
         */
        public static boolean isApplicationInBackground(Context context) {
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> taskList = am.getRunningTasks(1);
            if (taskList != null && !taskList.isEmpty()) {
                ComponentName topActivity = taskList.get(0).topActivity;
                return topActivity != null && !topActivity.getPackageName().equals(context.getPackageName());
            }
            return false;
        }

        public static boolean isServiceRunning(Context mContext, String className) {

            boolean isRunning = false;
            ActivityManager activityManager = (ActivityManager)
                    mContext.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningServiceInfo> serviceList
                    = activityManager.getRunningServices(30);

            if (!(serviceList.size() > 0)) {
                return false;
            }

            for (int i = 0; i < serviceList.size(); i++) {
                if (serviceList.get(i).service.getClassName().equals(className)) {
                    isRunning = true;
                    break;
                }
            }
            return isRunning;
        }
    }


    public static class Dimens {
        public static final int UNIT_DP = 1;
        public static final int UNIT_PX = 2;

        public static float dpToPx(Context context, float dp) {
            return dp * context.getResources().getDisplayMetrics().density;
        }

        public static float pxToDp(Context context, float px) {
            return px / context.getResources().getDisplayMetrics().density;
        }

        public static int dpToPxInt(Context context, float dp) {
            return (int) (dpToPx(context, dp) + 0.5f);
        }

        public static int pxToDpCeilInt(Context context, float px) {
            return (int) (pxToDp(context, px) + 0.5f);
        }

        public static DisplayMetrics getDisplayMetrics(Context context) {
            return context.getResources().getDisplayMetrics();
        }

        /**
         * 获取包含虚拟键的整体屏幕高度
         *
         * @return
         */
        public static int getScreenHeightAboveVirtualKeyboard(Context context) {
            WindowManager windowManager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
            Display display = windowManager.getDefaultDisplay();
            Point outPoint = new Point();
            display.getRealSize(outPoint);
            return outPoint.y;
        }

        public static int getNavBarHeight(Context ctx) {
            int result = 0;
            int resourceId = ctx.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = ctx.getResources().getDimensionPixelSize(resourceId);
            }
            return result;
        }

        @Retention(RetentionPolicy.SOURCE)
        @IntDef({UNIT_DP, UNIT_PX})
        public @interface DimensUnit {

        }

    }


    public static class Random {
        public static final String NUMBERS_AND_LETTERS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        public static final String NUMBERS = "0123456789";
        public static final String LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        public static final String CAPITAL_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        public static final String LOWER_CASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";

        public static String getRandomNumbersAndLetters(int length) {
            return getRandom(NUMBERS_AND_LETTERS, length);
        }

        public static String getRandomNumbers(int length) {
            return getRandom(NUMBERS, length);
        }

        public static String getRandomLetters(int length) {
            return getRandom(LETTERS, length);
        }

        public static String getRandomCapitalLetters(int length) {
            return getRandom(CAPITAL_LETTERS, length);
        }

        public static String getRandomLowerCaseLetters(int length) {
            return getRandom(LOWER_CASE_LETTERS, length);
        }

        public static String getRandom(String source, int length) {
            return TextUtils.isEmpty(source) ? null : getRandom(source.toCharArray(), length);
        }

        public static String getRandom(char[] sourceChar, int length) {
            if (sourceChar == null || sourceChar.length == 0 || length < 0) {
                return null;
            }

            StringBuilder str = new StringBuilder(length);
            java.util.Random random = new java.util.Random();
            for (int i = 0; i < length; i++) {
                str.append(sourceChar[random.nextInt(sourceChar.length)]);
            }
            return str.toString();
        }

        public static int getRandom(int max) {
            return getRandom(0, max);
        }

        /**
         * 该方法的作用是生成一个随机的int值，该值介于[min,max)的区间，也就是0到n之间的随机int值，包含min而不包含max。
         *
         * @param min
         * @param max
         * @return
         */
        public static int getRandom(int min, int max) {
            return min > max ? 0 : min == max ? min : min + new java.util.Random().nextInt(max - min);
        }
    }

    public static class File {
        public final static String FILE_EXTENSION_SEPARATOR = ".";

        /**
         * read file
         *
         * @param filePath
         * @param charsetName The name of a supported charset
         * @return if file not exist, return null, else return content of file
         */
        public static StringBuilder readFile(String filePath, String charsetName) {
            java.io.File file = new java.io.File(filePath);
            StringBuilder fileContent = new StringBuilder();
            if (!file.isFile()) {
                return new StringBuilder();
            }

            BufferedReader reader = null;
            try {
                InputStreamReader is = new InputStreamReader(new FileInputStream(file), charsetName);
                reader = new BufferedReader(is);
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!fileContent.toString().equals("")) {
                        fileContent.append("\r\n");
                    }
                    fileContent.append(line);
                }
                return fileContent;
            } catch (IOException e) {
                throw new RuntimeException("IOException occurred. ", e);
            } finally {
                IO.close(reader);
            }
        }

        //读取方法
        public static String getAsset(Context context, String fileName) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                BufferedReader bf = new BufferedReader(new InputStreamReader(context.getAssets().open(fileName)));
                String line;
                while ((line = bf.readLine()) != null) {
                    stringBuilder.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        /**
         * write file
         *
         * @param filePath
         * @param content
         * @param append   is append, if true, write to the end of file, else clear content of file and write into it
         * @return return false if content is empty, true otherwise
         */
        public static boolean writeFile(String filePath, String content, boolean append) {
            if (TextUtils.isEmpty(content)) {
                return false;
            }

            FileWriter fileWriter = null;
            try {
                makeDirs(filePath);
                fileWriter = new FileWriter(filePath, append);
                fileWriter.write(content);
                return true;
            } catch (IOException e) {
                throw new RuntimeException("IOException occurred. ", e);
            } finally {
                IO.close(fileWriter);
            }
        }

        public static java.io.File savePhotoRandom(Bitmap bmp) {
            String filePath = getFilePath().getPath() + java.io.File.separator + GlobalContext.getAppContext().getPackageName();
            new java.io.File(filePath).mkdirs();
            return savePhoto(bmp, new java.io.File(filePath, Kits.Date.getYmdhms(System.currentTimeMillis())).getAbsolutePath());
        }

        public static java.io.File savePhoto(String fileName, Bitmap bmp) {
//            String filePath = getFilePath().getPath() + java.io.File.separator + GlobalContext.getAppContext().getPackageName();
//            new java.io.File(filePath).mkdirs();
//            return savePhoto(bmp, new java.io.File(filePath, fileName).getAbsolutePath());

            String filePath = getFilePath().getPath() + java.io.File.separator + GlobalContext.getAppContext().getPackageName();
//            new java.io.File(filePath).mkdirs();

            ContextWrapper cw = new ContextWrapper(GlobalContext.getAppContext());
            java.io.File directory = cw.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            new java.io.File(directory, fileName).mkdirs();
            return savePhoto(bmp, new java.io.File(directory, fileName).getAbsolutePath());

        }

        public static java.io.File savePhoto(String fileName, Bitmap bmp, String extraPath) {
            String filePath = Kits.File.getFilePath().getPath() + java.io.File.separator + GlobalContext.getAppContext().getPackageName() + java.io.File.separator + extraPath;
            new java.io.File(filePath).mkdirs();
            return Kits.File.savePhoto(bmp, new java.io.File(filePath, fileName).getAbsolutePath());
        }

        public static java.io.File savePhoto(Bitmap bmp, String fileName, String parentPath) {
            return savePhoto(bmp, new java.io.File(parentPath, fileName).getAbsolutePath());
        }

        public static java.io.File savePhoto(Bitmap bmp, String filePath) {
            FileOutputStream fos = null;
            java.io.File file = null;
            try {
                String pathName;
                if (filePath.endsWith(".png")
                        || filePath.endsWith(".jpg")
                        || filePath.endsWith(".bmp")
                        || filePath.endsWith(".webp")
                        || filePath.endsWith(".jpeg")) {
                    pathName = filePath;
                } else {
                    pathName = filePath.concat(".png");
                }
                file = new java.io.File(pathName);
                fos = new FileOutputStream(file);
                bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.flush();
            } catch (IOException e) {
                IO.close(fos);
            }
            return file;
        }

        /**
         * 保存图片后发送广播通知更新数据库
         */
        public static void flushMedia(Context context, java.io.File file) {
            //android 4.4以后必须用以下方法插入相册图片，广播暂不需要，防止重复插入，已注释，不删
            try {
                MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), file.getName(), null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
//            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new java.io.File(file.getPath()))));
        }

        /**
         * 针对系统文夹只需要扫描,不用插入内容提供者,不然会重复
         *
         * @param context  上下文
         * @param filePath 文件路径
         */
        private static void scanFile(Context context, String filePath) {
//            if (!checkFile(filePath))
//                return;
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intent.setData(Uri.fromFile(new java.io.File(filePath)));
            context.sendBroadcast(intent);
        }

        public static boolean isSdCardAvailable() {
            return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                    && !Environment.isExternalStorageRemovable();
        }

        public static java.io.File getFilePath() {
            // 优先使用外缓存路径，如果没有挂载外存储，就使用内缓存路径
            java.io.File sdDir;
            if (isSdCardAvailable()) {
                sdDir = Environment.getExternalStorageDirectory();//获取根目录
            } else {
                sdDir = GlobalContext.getAppContext().getCacheDir();
            }
            return sdDir;
        }

        /**
         * write file
         *
         * @param filePath
         * @param contentList
         * @param append      is append, if true, write to the end of file, else clear content of file and write into it
         * @return return false if contentList is empty, true otherwise
         */
        public static boolean writeFile(String filePath, List<String> contentList, boolean append) {
            if (contentList == null || contentList.isEmpty()) {
                return false;
            }

            FileWriter fileWriter = null;
            try {
                makeDirs(filePath);
                fileWriter = new FileWriter(filePath, append);
                int i = 0;
                for (String line : contentList) {
                    if (i++ > 0) {
                        fileWriter.write("\r\n");
                    }
                    fileWriter.write(line);
                }
                return true;
            } catch (IOException e) {
                throw new RuntimeException("IOException occurred. ", e);
            } finally {
                IO.close(fileWriter);
            }
        }

        /**
         * write file, the string will be written to the begin of the file
         *
         * @param filePath
         * @param content
         * @return
         */
        public static boolean writeFile(String filePath, String content) {
            return writeFile(filePath, content, false);
        }

        /**
         * write file, the string list will be written to the begin of the file
         *
         * @param filePath
         * @param contentList
         * @return
         */
        public static boolean writeFile(String filePath, List<String> contentList) {
            return writeFile(filePath, contentList, false);
        }

        /**
         * write file, the bytes will be written to the begin of the file
         *
         * @param filePath
         * @param stream
         * @return
         */
        public static boolean writeFile(@NonNull String filePath, InputStream stream) {
            return writeFile(filePath, stream, false);
        }

        /**
         * write file
         *
         * @param stream the input stream
         * @param append if <code>true</code>, then bytes will be written to the end of the file rather than the beginning
         * @return return true
         */
        public static boolean writeFile(String filePath, InputStream stream, boolean append) {
            return writeFile(new java.io.File(filePath), stream, append);
        }

        /**
         * write file, the bytes will be written to the begin of the file
         *
         * @param file
         * @param stream
         */
        public static boolean writeFile(java.io.File file, InputStream stream) {
            return writeFile(file, stream, false);
        }

        /**
         * write file
         *
         * @param file   the file to be opened for writing.
         * @param stream the input stream
         * @param append if <code>true</code>, then bytes will be written to the end of the file rather than the beginning
         * @return return true
         */
        public static boolean writeFile(java.io.File file, InputStream stream, boolean append) {
            OutputStream o = null;
            try {
                makeDirs(file.getAbsolutePath());
                o = new FileOutputStream(file, append);
                byte[] data = new byte[1024];
                int length = -1;
                while ((length = stream.read(data)) != -1) {
                    o.write(data, 0, length);
                }
                o.flush();
                return true;
            } catch (FileNotFoundException e) {
                throw new RuntimeException("FileNotFoundException occurred. ", e);
            } catch (IOException e) {
                throw new RuntimeException("IOException occurred. ", e);
            } finally {
                IO.close(o);
                IO.close(stream);
            }
        }

        /**
         * move file
         *
         * @param sourceFilePath
         * @param destFilePath
         */
        public static void moveFile(String sourceFilePath, String destFilePath) {
            if (TextUtils.isEmpty(sourceFilePath) || TextUtils.isEmpty(destFilePath)) {
                throw new RuntimeException("Both sourceFilePath and destFilePath cannot be null.");
            }
            moveFile(new java.io.File(sourceFilePath), new java.io.File(destFilePath));
        }

        /**
         * move file
         *
         * @param srcFile
         * @param destFile
         */
        public static void moveFile(java.io.File srcFile, java.io.File destFile) {
            boolean rename = srcFile.renameTo(destFile);
            if (!rename) {
                copyFile(srcFile.getAbsolutePath(), destFile.getAbsolutePath());
                deleteFile(srcFile.getAbsolutePath());
            }
        }

        /**
         * copy file
         *
         * @param sourceFilePath
         * @param destFilePath
         * @return
         */
        public static boolean copyFile(String sourceFilePath, String destFilePath) {
            InputStream inputStream = null;
            try {
                inputStream = new FileInputStream(sourceFilePath);
            } catch (FileNotFoundException e) {
                throw new RuntimeException("FileNotFoundException occurred. ", e);
            }
            return writeFile(destFilePath, inputStream);
        }

        /**
         * read file to string list, a element of list is a line
         *
         * @param filePath
         * @param charsetName The name of a supported charset
         * @return if file not exist, return null, else return content of file
         */
        public static List<String> readFileToList(String filePath, String charsetName) {
            java.io.File file = new java.io.File(filePath);
            List<String> fileContent = new ArrayList<String>();
            if (!file.isFile()) {
                return null;
            }

            BufferedReader reader = null;
            try {
                InputStreamReader is = new InputStreamReader(new FileInputStream(file), charsetName);
                reader = new BufferedReader(is);
                String line = null;
                while ((line = reader.readLine()) != null) {
                    fileContent.add(line);
                }
                return fileContent;
            } catch (IOException e) {
                throw new RuntimeException("IOException occurred. ", e);
            } finally {
                IO.close(reader);
            }
        }

        /**
         * get file name from path, not include suffix
         * <p/>
         * <pre>
         *      getFileNameWithoutExtension(null)               =   null
         *      getFileNameWithoutExtension("")                 =   ""
         *      getFileNameWithoutExtension("   ")              =   "   "
         *      getFileNameWithoutExtension("abc")              =   "abc"
         *      getFileNameWithoutExtension("a.mp3")            =   "a"
         *      getFileNameWithoutExtension("a.b.rmvb")         =   "a.b"
         *      getFileNameWithoutExtension("c:\\")              =   ""
         *      getFileNameWithoutExtension("c:\\a")             =   "a"
         *      getFileNameWithoutExtension("c:\\a.b")           =   "a"
         *      getFileNameWithoutExtension("c:a.txt\\a")        =   "a"
         *      getFileNameWithoutExtension("/home/admin")      =   "admin"
         *      getFileNameWithoutExtension("/home/admin/a.txt/b.mp3")  =   "b"
         * </pre>
         *
         * @param filePath
         * @return file name from path, not include suffix
         * @see
         */
        public static String getFileNameWithoutExtension(String filePath) {
            if (TextUtils.isEmpty(filePath)) {
                return filePath;
            }

            int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
            int filePosi = filePath.lastIndexOf(java.io.File.separator);
            if (filePosi == -1) {
                return (extenPosi == -1 ? filePath : filePath.substring(0, extenPosi));
            }
            if (extenPosi == -1) {
                return filePath.substring(filePosi + 1);
            }
            return (filePosi < extenPosi ? filePath.substring(filePosi + 1, extenPosi) : filePath.substring(filePosi + 1));
        }

        /**
         * get file name from path, include suffix
         * <p/>
         * <pre>
         *      getFileName(null)               =   null
         *      getFileName("")                 =   ""
         *      getFileName("   ")              =   "   "
         *      getFileName("a.mp3")            =   "a.mp3"
         *      getFileName("a.b.rmvb")         =   "a.b.rmvb"
         *      getFileName("abc")              =   "abc"
         *      getFileName("c:\\")              =   ""
         *      getFileName("c:\\a")             =   "a"
         *      getFileName("c:\\a.b")           =   "a.b"
         *      getFileName("c:a.txt\\a")        =   "a"
         *      getFileName("/home/admin")      =   "admin"
         *      getFileName("/home/admin/a.txt/b.mp3")  =   "b.mp3"
         * </pre>
         *
         * @param filePath
         * @return file name from path, include suffix
         */
        public static String getFileName(String filePath) {
            if (TextUtils.isEmpty(filePath)) {
                return filePath;
            }

            int filePosi = filePath.lastIndexOf(java.io.File.separator);
            return (filePosi == -1) ? filePath : filePath.substring(filePosi + 1);
        }

        /**
         * get folder name from path
         * <p/>
         * <pre>
         *      getFolderName(null)               =   null
         *      getFolderName("")                 =   ""
         *      getFolderName("   ")              =   ""
         *      getFolderName("a.mp3")            =   ""
         *      getFolderName("a.b.rmvb")         =   ""
         *      getFolderName("abc")              =   ""
         *      getFolderName("c:\\")              =   "c:"
         *      getFolderName("c:\\a")             =   "c:"
         *      getFolderName("c:\\a.b")           =   "c:"
         *      getFolderName("c:a.txt\\a")        =   "c:a.txt"
         *      getFolderName("c:a\\b\\c\\d.txt")    =   "c:a\\b\\c"
         *      getFolderName("/home/admin")      =   "/home"
         *      getFolderName("/home/admin/a.txt/b.mp3")  =   "/home/admin/a.txt"
         * </pre>
         *
         * @param filePath
         * @return
         */
        public static String getFolderName(String filePath) {

            if (TextUtils.isEmpty(filePath)) {
                return filePath;
            }

            int filePosi = filePath.lastIndexOf(java.io.File.separator);
            return (filePosi == -1) ? "" : filePath.substring(0, filePosi);
        }

        /**
         * get suffix of file from path
         * <p/>
         * <pre>
         *      getFileExtension(null)               =   ""
         *      getFileExtension("")                 =   ""
         *      getFileExtension("   ")              =   "   "
         *      getFileExtension("a.mp3")            =   "mp3"
         *      getFileExtension("a.b.rmvb")         =   "rmvb"
         *      getFileExtension("abc")              =   ""
         *      getFileExtension("c:\\")              =   ""
         *      getFileExtension("c:\\a")             =   ""
         *      getFileExtension("c:\\a.b")           =   "b"
         *      getFileExtension("c:a.txt\\a")        =   ""
         *      getFileExtension("/home/admin")      =   ""
         *      getFileExtension("/home/admin/a.txt/b")  =   ""
         *      getFileExtension("/home/admin/a.txt/b.mp3")  =   "mp3"
         * </pre>
         *
         * @param filePath
         * @return
         */
        public static String getFileExtension(String filePath) {
            if (TextUtils.isEmpty(filePath)) {
                return filePath;
            }

            int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
            int filePosi = filePath.lastIndexOf(java.io.File.separator);
            if (extenPosi == -1) {
                return "";
            }
            return (filePosi >= extenPosi) ? "" : filePath.substring(extenPosi + 1);
        }

        /**
         * Creates the directory named by the trailing filename of this file, including the complete directory path required
         * to create this directory. <br/>
         * <br/>
         * <ul>
         * <strong>Attentions:</strong>
         * <li>makeDirs("C:\\Users\\Trinea") can only create users folder</li>
         * <li>makeFolder("C:\\Users\\Trinea\\") can create Trinea folder</li>
         * </ul>
         *
         * @param filePath
         * @return true if the necessary directories have been created or the target directory already exists, false one of
         * the directories can not be created.
         * <ul>
         * <li>if {@link File#getFolderName} return null, return false</li>
         * <li>if target directory already exists, return true</li>
         * </ul>
         */
        public static boolean makeDirs(String filePath) {
            String folderName = getFolderName(filePath);
            if (TextUtils.isEmpty(folderName)) {
                return false;
            }

            java.io.File folder = new java.io.File(folderName);
            return (folder.exists() && folder.isDirectory()) || folder.mkdirs();
        }

        /**
         * @param filePath
         */
        public static boolean makeFolders(String filePath) {
            return makeDirs(filePath);
        }

        /**
         * @param filePath
         */
        public static boolean createFile(String filePath, String fileName) {
            try {
                return new java.io.File(filePath + java.io.File.separator + fileName).createNewFile();
            } catch (IOException e) {
                return false;
            }
        }

        /**
         * Indicates if this file represents a file on the underlying file system.
         *
         * @param filePath
         * @return
         */
        public static boolean isFileExist(String filePath) {
            if (TextUtils.isEmpty(filePath)) {
                return false;
            }
            java.io.File file = new java.io.File(filePath);
            return (file.exists() && file.isFile());
        }

        /**
         * Indicates if this file represents a directory on the underlying file system.
         *
         * @param directoryPath
         * @return
         */
        public static boolean isFolderExist(String directoryPath) {
            if (TextUtils.isEmpty(directoryPath)) {
                return false;
            }
            java.io.File dire = new java.io.File(directoryPath);
            return (dire.exists() && dire.isDirectory());
        }

        /**
         * delete file or directory
         * <ul>
         * <li>if path is null or empty, return true</li>
         * <li>if path not exist, return true</li>
         * <li>if path exist, delete recursion. return true</li>
         * <ul>
         *
         * @param path
         * @return
         */
        public static boolean deleteFile(String path) {
            if (TextUtils.isEmpty(path)) {
                return true;
            }

            java.io.File file = new java.io.File(path);
            if (!file.exists()) {
                return true;
            }
            if (file.isFile()) {
                return file.delete();
            }
            if (!file.isDirectory()) {
                return false;
            }
            for (java.io.File f : file.listFiles()) {
                if (f.isFile()) {
                    f.delete();
                } else if (f.isDirectory()) {
                    deleteFile(f.getAbsolutePath());
                }
            }
            return file.delete();
        }

        /**
         * get file size
         * <ul>
         * <li>if path is null or empty, return -1</li>
         * <li>if path exist and it is a file, return file size, else return -1</li>
         * <ul>
         *
         * @param path
         * @return returns the length of this file in bytes. returns -1 if the file does not exist.
         */
        public static long getFileSize(String path) {
            if (TextUtils.isEmpty(path)) {
                return -1;
            }

            java.io.File file = new java.io.File(path);
            return (file.exists() && file.isFile() ? file.length() : -1);
        }

    }

    public static class IO {
        /**
         * 关闭流
         */
        public static void close(Closeable closeable) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    throw new RuntimeException("IOException occurred. ", e);
                }
            }
        }
    }

    public static class Date {

        public static final String FORMAT_DATE = "yyyy-MM-dd";
        public static final String FORMAT_DATE_ZH = "yyyy年MM月dd日";
        public static final String FORMAT_ACTIVITY_TIME = "yyyy.MM.dd";
        public static final String FORMAT_LOTTERY_TIME = "MM/dd HH:mm";
        private final static ThreadLocal<SimpleDateFormat> DATE_FORMATER = new ThreadLocal<SimpleDateFormat>() {
            @Override
            protected SimpleDateFormat initialValue() {
                return new SimpleDateFormat("yyyy-MM-dd");
            }
        };
        private final static String YEAR_CHINESE = "年";
        private final static String MONTH_CHINESE = "月";
        private final static String DAY_CHINESE = "日";
        private static final HashMap<Integer, String> intToChinese = new HashMap<>();
        /**
         * describe：计算天干地支,12生肖
         * <p>
         * 计算规则相对简单，详细计算规则请参照百度百科http://baike.baidu.com/view/13672.htm
         */

        private final static String[][] tgdz = new String[][]{
                {"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"}//10天干
                , {"子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"}};//12地支
        //12生肖，（注：12生肖对应12地支，即子鼠，丑牛,寅虎依此类推）
        private final static String[] animalYear = new String[]{"鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪"};
        private final static int startYear = 1804;//定义起始年，1804年为甲子年属鼠
        private static SimpleDateFormat mm = new SimpleDateFormat("MM", Locale.getDefault());
        private static SimpleDateFormat HH = new SimpleDateFormat("HH", Locale.getDefault());
        private static SimpleDateFormat hh = new SimpleDateFormat("hh", Locale.getDefault());
        private static SimpleDateFormat d = new SimpleDateFormat("dd", Locale.getDefault());
        private static SimpleDateFormat md = new SimpleDateFormat("MM-dd", Locale.getDefault());
        private static SimpleDateFormat mdc = new SimpleDateFormat("MM月dd日", Locale.getDefault());
        private static SimpleDateFormat ymdc = new SimpleDateFormat(FORMAT_DATE_ZH, Locale.getDefault());
        private static SimpleDateFormat ymd = new SimpleDateFormat(FORMAT_DATE, Locale.getDefault());
        private static SimpleDateFormat ymdDot = new SimpleDateFormat(FORMAT_ACTIVITY_TIME, Locale.getDefault());
        private static SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        private static SimpleDateFormat dmyhms = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        private static SimpleDateFormat mdyhms = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault());
        private static SimpleDateFormat hms = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        private static SimpleDateFormat ms = new SimpleDateFormat("mm:ss", Locale.getDefault());
        private static SimpleDateFormat ymdhmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());
        private static SimpleDateFormat ymdhm = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        private static SimpleDateFormat hm = new SimpleDateFormat("HH:mm", Locale.getDefault());
        private static SimpleDateFormat mdhm = new SimpleDateFormat("MM月dd日 HH:mm", Locale.getDefault());
        private static SimpleDateFormat mdhmLink = new SimpleDateFormat("MM/dd HH:mm", Locale.getDefault());
        private static SimpleDateFormat mdhmsLink = new SimpleDateFormat("MM-dd HH:mm:ss", Locale.getDefault());
        private static SimpleDateFormat mdhmLink2 = new SimpleDateFormat(FORMAT_LOTTERY_TIME, Locale.getDefault());
        private static SimpleDateFormat mdhmLink3 = new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault());
        private static SimpleDateFormat ymdhmsCh = new SimpleDateFormat("yyyy年MM月dd日 HH:mm", Locale.getDefault());

        static {
            intToChinese.put(0, "零");
            intToChinese.put(1, "一");
            intToChinese.put(2, "二");
            intToChinese.put(3, "三");
            intToChinese.put(4, "四");
            intToChinese.put(5, "五");
            intToChinese.put(6, "六");
            intToChinese.put(7, "七");
            intToChinese.put(8, "八");
            intToChinese.put(9, "九");
            intToChinese.put(10, "十");
        }

        /**
         * 以友好的方式显示时间
         */
        public static String getFriendlyTime(long time) {
            if (time == 0L) {
                return "";
            }
            java.util.Date date = new java.util.Date(time);
            String ftime = "";
            Calendar cal = Calendar.getInstance();

            // 判断是否是同一天
            String curDate = DATE_FORMATER.get().format(cal.getTime());
            String paramDate = DATE_FORMATER.get().format(date);
            if (curDate.equals(paramDate)) {
                int hour = (int) ((cal.getTimeInMillis() - date.getTime()) / 3600000);
                if (hour == 0) {
                    ftime = Math.max((cal.getTimeInMillis() - date.getTime()) / 60000, 1) + "分钟前";
                } else {
                    ftime = hour + "小时前";
                }
                return ftime;
            }

            long lt = date.getTime() / 86400000;
            long ct = cal.getTimeInMillis() / 86400000;
            int days = (int) (ct - lt);
            if (days == 0) {
                int hour = (int) ((cal.getTimeInMillis() - date.getTime()) / 3600000);
                if (hour == 0) {
                    ftime = Math.max((cal.getTimeInMillis() - date.getTime()) / 60000, 1) + "分钟前";
                } else {
                    ftime = hour + "小时前";
                }
            } else if (days == 1) {
                ftime = "昨天";
            } else if (days == 2) {
                ftime = "前天";
            } else if (days > 2 && days <= 10) {
                ftime = days + "天前";
            } else if (days > 10) {
                ftime = DATE_FORMATER.get().format(date);
            }
            return ftime;
        }

        /**
         * 将年月日转为日期类型
         */
        public static java.util.Date getTimeByYmD(int year, int month, int day) {
            try {
                return DATE_FORMATER.get().parse(year + "-" + month + "-" + day);
            } catch (ParseException e) {
                return null;
            }
        }

        /**
         * 将字符串转为日期类型
         *
         * @param sdate
         * @return
         */
        public static java.util.Date toDate(String sdate) {
            try {
                return DATE_FORMATER.get().parse(sdate);
            } catch (ParseException e) {
                return null;
            }
        }


        /**
         * 年月日[2015-07-28]
         */
        public static String getYmd(long timeInMills) {
            return ymd.format(new java.util.Date(timeInMills));
        }

        /**
         * 年月日[2015-07-28]
         */
        public static String getYmd(java.util.Date date) {
            return ymd.format(date);
        }

        /**
         * 年月日[2015.07.28]
         *
         * @param timeInMills
         * @return
         */
        public static String getYmdDot(long timeInMills) {
            return ymdDot.format(new java.util.Date(timeInMills));
        }

        public static String getYmdhms(long timeInMills) {
            return ymdhms.format(new java.util.Date(timeInMills));
        }

        public static String getHms(long timeInMills) {
            return hms.format(new java.util.Date(timeInMills));
        }

        public static String getMs(long timeInMills) {
            return ms.format(new java.util.Date(timeInMills));
        }

        public static String getYmdhmsS(long timeInMills) {
            return ymdhmss.format(new java.util.Date(timeInMills));
        }

        public static String getYmdhm(long timeInMills) {
            return ymdhm.format(new java.util.Date(timeInMills));
        }
        public static String getYmdhmCh(long timeInMills) {
            return ymdhmsCh.format(new java.util.Date(timeInMills));
        }

        public static String getHm(long timeInMills) {
            return hm.format(new java.util.Date(timeInMills));
        }

        public static String getMd(long timeInMills) {
            return md.format(new java.util.Date(timeInMills));
        }

        public static String getMdc(long timeInMills) {
            return mdc.format(new java.util.Date(timeInMills));
        }

        public static String getYmdc(long timeInMills) {
            return ymdc.format(new java.util.Date(timeInMills));
        }

        public static String getMdhm(long timeInMills) {
            return mdhm.format(new java.util.Date(timeInMills));
        }

        public static String getMdhmLink(long timeInMills) {
            return mdhmLink.format(new java.util.Date(timeInMills));
        }

        public static String getMdhmsLink(long timeInMills) {
            return mdhmsLink.format(new java.util.Date(timeInMills));
        }

        public static String getMdhmLink2(long timeInMills) {
            return mdhmLink2.format(new java.util.Date(timeInMills));
        }

        public static String getMdhmLink3(long timeInMills) {
            return mdhmLink3.format(new java.util.Date(timeInMills));
        }


        public static String getMM(long timeInMills) {
            return mm.format(new java.util.Date(timeInMills));
        }

        public static String getHH(long timeInMills) {
            return HH.format(new java.util.Date(timeInMills));
        }

        public static String getD(long timeInMills) {
            return d.format(new java.util.Date(timeInMills));
        }

//        public static String getDmyhms(long timeInMills) {
//            return dmyhms.format(new java.util.Date(timeInMills));
//        }
        public static String getMdyhms(long timeInMills) {
            return mdyhms.format(new java.util.Date(timeInMills));
        }
        public static boolean isNight() {
            return isNight(System.currentTimeMillis());
        }

        public static boolean isNight(long timeInMills) {
            String hour = HH.format(new java.util.Date(timeInMills));
            int hourInt = Integer.parseInt(hour);
            //晚上 / 白天
            return (hourInt >= 0 && hourInt < 7) || (hourInt >= 19 && hourInt < 24);
        }

        /**
         * 是否是今天
         *
         * @param timeInMills
         * @return
         */
        public static boolean isToday(long timeInMills) {
            String dest = getYmd(timeInMills);
            String now = getYmd(Calendar.getInstance().getTimeInMillis());
            return dest.equals(now);
        }

        /**
         * 是否是同一天
         *
         * @param aMills
         * @return
         */
        public static boolean isSameDay(long aMills) {
            return isSameDay(aMills, System.currentTimeMillis());
        }

        public static boolean isSameDay(long aMills, long bMills) {
            String aDay = getYmd(aMills);
            String bDay = getYmd(bMills);
            return aDay.equals(bDay);
        }

        private static String yearToChinese(int year) {
            StringBuilder yearString = new StringBuilder();
            while (year > 0) {
                int y = year % 10;
                yearString.insert(0, intToChinese.get(y));
                year = year / 10;
            }
            return yearString.toString();
        }

        // format : 12->"十二"
        private static String otherToChinese(int dayOrMonth) {
            if (dayOrMonth < 0) {
                return "";
            }
            if (dayOrMonth < 10) {
                return intToChinese.get(dayOrMonth);
            }
            int tens = dayOrMonth / 10;
            int units = dayOrMonth - tens * 10;
            return ((tens == 1 ? "" : intToChinese.get(tens)) + "十") +
                    ((units <= 0) ? "" : intToChinese.get(units));
        }

        // format : "二零一五年 九月 十一日"
        public static String getFullCNDate(long dateSeconds) {
            return getYear(Kits.Date.getYear(dateSeconds))
                    + getMonth(Kits.Date.getMonth(dateSeconds))
                    + getDay(Kits.Date.getDay(dateSeconds));
        }

        // format : "十月二十五日"
        public static String getMonthDayCNDate(long dateSeconds) {
            return getMonth(Kits.Date.getMonth(dateSeconds))
                    + getDay(Kits.Date.getDay(dateSeconds));
        }

        // format : "二零一六年 九月"
        public static String getYearMonthCNData(long dateSeconds) {
            return getYear(Kits.Date.getYear(dateSeconds))
                    + getMonth(Kits.Date.getMonth(dateSeconds));
        }

        // format : 二十五日
        public static String getDayCNData(long dateSeconds) {
            return getDay(Kits.Date.getDay(dateSeconds));
        }

        public static String getDay(int day) {
            return getPureDay(day) + DAY_CHINESE + " ";
        }

        public static String getYear(int year) {
            return getPureYear(year) + YEAR_CHINESE + " ";
        }

        public static String getMonth(int month) {
            return getPureMonth(month) + MONTH_CHINESE + " ";
        }

        public static String getSayHello() {
            int hours = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            if (hours > 6 && hours <= 11) {
                return "早上好";
            } else if (hours > 11 && hours <= 13) {
                return "中午好";
            } else if (hours > 13 && hours <= 18) {
                return "下午好";
            } else if (hours == 19) {
                return "傍晚好";
            } else if (hours > 19) {
                return "晚上好";
            } else {
                return "夜深了";
            }
        }

        public static String getPureDay(int day) {
            return otherToChinese(day);
        }

        public static String getPureYear(int year) {
            return yearToChinese(year);
        }

        public static String getPureMonth(int month) {
            return otherToChinese(month);
        }

        /**
         * 获取当前年份与起始年之间的差值
         **/
        public static int subtractYear(int year) {
            int jiaziYear = startYear;
            if (year < jiaziYear) {//如果年份小于起始的甲子年(startYear = 1804),则起始甲子年往前偏移
                jiaziYear = jiaziYear - (60 + 60 * ((jiaziYear - year) / 60));//60年一个周期
            }
            return year - jiaziYear;
        }

        /**
         * 获取该年的天干名称
         */
        public static String getHeavenlyStemsYear(int year) {
            return tgdz[0][subtractYear(year) % 10];
        }

        /**
         * 获取该年的地支名称
         */
        public static String getEarthlyBranchesYear(int year) {
            return tgdz[1][subtractYear(year) % 12];
        }

        /**
         * 获取该年的天干、地支名称
         *
         * @param year 年份
         */
        public static String getTraditionalYear(int year) {
            return getHeavenlyStemsYear(year) + getEarthlyBranchesYear(year);
        }

        /**
         * 获取该年的生肖名称
         *
         * @param year 年份
         */
        public static String getAnimalYearName(int year) {
            return animalYear[subtractYear(year) % 12];
        }

        /**
         * 计算两个日期之间相差的天数
         *
         * @param smdate 较小的时间
         * @param bdate  较大的时间
         * @return 相差天数
         */
        public static int daysBetween(java.util.Date smdate, java.util.Date bdate) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(smdate);
            long time1 = cal.getTimeInMillis();
            cal.setTime(bdate);
            long time2 = cal.getTimeInMillis();
            long betweenDays = (time2 - time1) / (1000 * 3600 * 24);
            return Integer.parseInt(String.valueOf(betweenDays));
        }

        /**
         * 计算两个日期之间相差的天数
         *
         * @param timeInMillisB 较大的时间
         * @param timeInMillisS 较小的时间
         * @return 相差天数
         */
        public static int daysBetween(long timeInMillisB, long timeInMillisS) {
            return (int) ((timeInMillisB - timeInMillisS) / (1000 * 3600 * 24));
        }

        /**
         * 获取今天还剩下多少秒
         *
         * @return
         */
        public static int getRightSeconds() {
            Calendar curDate = Calendar.getInstance();
            Calendar tommorowDate = new GregorianCalendar(curDate
                    .get(Calendar.YEAR), curDate.get(Calendar.MONTH), curDate
                    .get(Calendar.DATE) + 1, 0, 0, 0);
            return (int) (tommorowDate.getTimeInMillis() - curDate.getTimeInMillis()) / 1000;
        }

        /**
         * 返回date1-dat2相差的秒数
         *
         * @param date1
         * @param date2
         * @return     
         */

        public static int subSecond(java.util.Date date1, java.util.Date date2) {
            long d1 = date1.getTime();
            long d2 = date2.getTime();
            return (int) ((d1 - d2) / 1000);
        }

        /**
         * 获取年份
         */
        public static int getYear(long mills) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(mills);
            return calendar.get(Calendar.YEAR);
        }

        /**
         * 获取月份
         */
        public static int getMonth(long mills) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(mills);
            return calendar.get(Calendar.MONTH) + 1;
        }

        /**
         * 获取日
         */
        public static int getDay(long mills) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(mills);
            return calendar.get(Calendar.DAY_OF_MONTH);
        }

        /**
         * 获取 HourOfDay
         */
        public static int getHourOfDay(long mills) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(mills);
            return calendar.get(Calendar.HOUR_OF_DAY);
        }


        /**
         * 获取月份的天数
         */
        public static int getDaysInMonth(long mills) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(mills);

            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);

            switch (month) {
                case Calendar.JANUARY:
                case Calendar.MARCH:
                case Calendar.MAY:
                case Calendar.JULY:
                case Calendar.AUGUST:
                case Calendar.OCTOBER:
                case Calendar.DECEMBER:
                    return 31;
                case Calendar.APRIL:
                case Calendar.JUNE:
                case Calendar.SEPTEMBER:
                case Calendar.NOVEMBER:
                    return 30;
                case Calendar.FEBRUARY:
                    return (year % 4 == 0) ? 29 : 28;
                default:
                    throw new IllegalArgumentException("Invalid Month");
            }
        }

        /**
         * 获取星期,0-周日,1-周一，2-周二，3-周三，4-周四，5-周五，6-周六
         */
        public static int getWeek(long mills) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(mills);
            return calendar.get(Calendar.DAY_OF_WEEK) - 1;
        }

        public static String getWeek(long mills, String unit) {
            switch (getWeek(mills)) {
                case 0:
                    return unit + "日";
                case 1:
                    return unit + "一";
                case 2:
                    return unit + "二";
                case 3:
                    return unit + "三";
                case 4:
                    return unit + "四";
                case 5:
                    return unit + "五";
                case 6:
                    return unit + "六";
                default:
                    return unit + "一";
            }
        }
    }


    public static class Arith {
        //默认除法运算精度
        private static final int DEF_DIV_SCALE = 10;

        //加
        public static double add(double v1, double v2) {
            BigDecimal b1 = new BigDecimal(Double.toString(v1));
            BigDecimal b2 = new BigDecimal(Double.toString(v2));
            return b1.add(b2).doubleValue();
        }

        //减
        public static double sub(double v1, double v2) {
            BigDecimal b1 = new BigDecimal(Double.toString(v1));
            BigDecimal b2 = new BigDecimal(Double.toString(v2));
            return b1.subtract(b2).doubleValue();
        }

        //乘
        public static double mul(double v1, double v2) {
            BigDecimal b1 = new BigDecimal(Double.toString(v1));
            BigDecimal b2 = new BigDecimal(Double.toString(v2));
            return b1.multiply(b2).doubleValue();
        }

        //除
        public static double div(double v1, double v2) {
            return div(v1, v2, DEF_DIV_SCALE);
        }


        public static double div(double v1, double v2, int scale) {
            if (scale < 0) {
                throw new IllegalArgumentException("The scale must be a positive integer or zero");
            }
            BigDecimal b1 = new BigDecimal(Double.toString(v1));
            BigDecimal b2 = new BigDecimal(Double.toString(v2));
            return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        }

        public static float divf(float v1, float v2) {
            return divf(v1, v2, DEF_DIV_SCALE);
        }

        //除->保留小数位
        public static float divf(float v1, float v2, int scale) {
            if (scale < 0) {
                throw new IllegalArgumentException("The scale must be a positive integer or zero");
            }
            BigDecimal b1 = new BigDecimal(Float.toString(v1));
            BigDecimal b2 = new BigDecimal(Float.toString(v2));
            return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).floatValue();
        }  //除->保留小数位

        //取小数位
        public static double round(double v, int scale) {
            if (scale < 0) {
                throw new IllegalArgumentException("The scale must be a positive integer or zero");
            }
            BigDecimal b = new BigDecimal(Double.toString(v));
            BigDecimal one = new BigDecimal("1");
            return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        }

        public static float parseFloat(String v1) {
            BigDecimal b1 = new BigDecimal(v1);
            return b1.floatValue();
        }

        public static double parseDouble(String v1) {
            BigDecimal b1 = new BigDecimal(v1);
            return b1.doubleValue();
        }
    }

    public static class Text {

        public static String getTextTrim(TextView textView) {
            return textView.getText().toString().trim();
        }

        public static String getText(TextView textView) {
            return textView.getText().toString();
        }

        public static int getTextInt(TextView textView) {
            return Integer.parseInt(textView.getText().toString());
        }

        public static float getTextFloat(TextView textView) {
            return Float.parseFloat(textView.getText().toString());
        }
    }

    public static class Empty {

        public static boolean check(Object obj) {
            return obj == null;
        }

        public static boolean checkTextTrim(TextView textView) {
            return TextUtils.isEmpty(Text.getTextTrim(textView));
        }

        public static boolean checkText(TextView textView) {
            return TextUtils.isEmpty(Text.getText(textView));
        }

        public static boolean check(List list) {
            return list == null || list.isEmpty();
        }

        public static boolean check(SparseArray sparseArray) {
            return sparseArray == null || sparseArray.size() == 0;
        }

        public static boolean check(Object[] array) {
            return array == null || array.length == 0;
        }

        public static boolean check(CharSequence str) {
            return str == null || "".equals(str.toString());
        }

        public static boolean check(String str) {
            return str == null || "".equals(str);
        }

        public static boolean check(String... str) {
            if (str == null) {
                return true;
            }
            for (String s : str) {
                if (TextUtils.isEmpty(s)) {
                    return true;
                }
            }
            return false;
        }

        public static boolean check(Map map) {
            return map == null || map.isEmpty();
        }

        public static boolean check(Set set) {
            return set == null || set.isEmpty();
        }


    }


}
