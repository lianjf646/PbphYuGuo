//package com.pbph.yuguo.myview;
//
//import android.content.ClipData;
//import android.content.ClipboardManager;
//import android.content.ComponentName;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.drawable.BitmapDrawable;
//import android.net.Uri;
//import android.os.Environment;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup.LayoutParams;
//import android.widget.PopupWindow;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.engine.DiskCacheStrategy;
//import com.bumptech.glide.request.target.Target;
//import com.pbph.yuguo.R;
//import com.pbph.yuguo.constant.ConstantData;
//import com.pbph.yuguo.http.HttpAction;
//import com.pbph.yuguo.util.AMUtils;
//import com.pbph.yuguo.util.AlbumNotifyHelper;
//import com.pbph.yuguo.util.FileUtil;
//import com.pbph.yuguo.util.MD5Utils;
//import com.pbph.yuguo.wxutil.WechatUtils;
//import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
//
//import io.reactivex.Flowable;
//import io.reactivex.Observable;
//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.schedulers.Schedulers;
//import okhttp3.ResponseBody;
//
//
//public class SharePop extends PopupWindow {
//
//
//    Context context;
//
//    private LayoutInflater inflater;
//
//
//    View showView;
//
//
//    private TextView tvWx;
//    private TextView tvPyq;
//
//
//    private TextView tvDown;
//    private TextView textView;
//
//
//    SelectBusinessSchoolLeaderListResponse.DataBean.ListBean vo;
//    Object[] objects;
//
//    public SharePop(Context context) {
//        this(context, null);
//    }
//
//    public SharePop(Context context, View showView) {
//        super(context);
//
//        this.context = context;
//
//        inflater = LayoutInflater.from(context);
//
//        setBackgroundDrawable(new BitmapDrawable());
//        // 设置SelectPicPopupWindow弹出窗体的宽
//        // MainActivity.screenWidth / 4
//        this.setWidth(LayoutParams.MATCH_PARENT);
//        // 设置SelectPicPopupWindow弹出窗体的高
//        this.setHeight(LayoutParams.MATCH_PARENT);
//
//        // 设置SelectPicPopupWindow弹出窗体可点击
//        this.setFocusable(true);
//        this.setOutsideTouchable(true);
//
//        View view = inflater.inflate(R.layout.pop_share, null);
//
//
//        tvWx = (TextView) view.findViewById(R.id.tv_Wx);
//        tvWx.setOnClickListener(listener);
//        tvPyq = (TextView) view.findViewById(R.id.tv_Pyq);
//        tvPyq.setOnClickListener(listener);
//
//        tvDown = (TextView) view.findViewById(R.id.tv_Down);
//        tvDown.setOnClickListener(listener);
//
//        textView = (TextView) view.findViewById(R.id.textView);
//        textView.setOnClickListener(v -> dismiss());
//        // 设置SelectPicPopupWindow的View
//        this.setContentView(view);
//
//        view.setOnClickListener(new OnSingleClickListener() {
//            @Override
//            public void onCanClick(View v) {
//                dismiss();
//            }
//        });
//
//        // 刷新状态
//        this.update();
//
//        this.showView = showView;
//
//    }
//
//    public void isShowDownLoad(int type) {
//        switch (type) {
//            case 0:
//                tvWx.setVisibility(View.VISIBLE);
//                tvPyq.setVisibility(View.VISIBLE);
//
//                tvDown.setVisibility(View.GONE);
//                break;
//            case 1:
//                tvWx.setVisibility(View.VISIBLE);
//                tvPyq.setVisibility(View.VISIBLE);
//
//                tvDown.setVisibility(View.VISIBLE);
//                break;
//            case 2:
//                tvWx.setVisibility(View.GONE);
//                tvPyq.setVisibility(View.GONE);
//
//                tvDown.setVisibility(View.VISIBLE);
//                break;
//        }
//    }
//
//    public void show() {
//        show(showView);
//    }
//
//    public void show(View view) {
//        this.showAtLocation(view, Gravity.NO_GRAVITY, 0, 0);
//    }
//
//
//    OnSingleClickListener listener = new OnSingleClickListener() {
//        @Override
//        public void onCanClick(View v) {
//            switch (v.getId()) {
//                case R.id.tv_Wx: {
//                    share(true);
//                }
//                break;
//                case R.id.tv_Pyq: {
//                    share(false);
//                }
//                break;
//                case R.id.tv_Down: {
//                    down();
//                }
//                break;
//            }
//            dismiss();
//        }
//    };
//
//    private void down() {
//        WaitUI.Show(context);
//
//        SelectBusinessSchoolLeaderListResponse.DataBean.ListBean.BusinessSchoolLeaderBean leader = vo.getBusinessSchoolLeader();
//        //                            articleClass;//分类（0链接1视频2图文）
//        int type = Integer.parseInt(leader.getArticleClass());
//        switch (type) {
//            case 0: {
//            }
//            break;
//            case 1: {
//                downVidio(leader.getVideoUrl());
//            }
//            break;
//            case 2: {
//                downPhotos(vo.getBusinessSchoolImgList());
//            }
//            break;
//        }
//
//
//    }
//
//    private void downPhotos(List<String> list) {
//        Flowable<ResponseBody> a = Flowable.fromIterable(list).flatMap(s -> {
//            Log.e("a==", Thread.currentThread().getName());
//            return HttpAction.getInstance().get(s);
//        });
//        Flowable<File> b = Flowable.fromIterable(list).map(s -> {
//            Log.e("b==", Thread.currentThread().getName());
//            String sfx = s.substring(s.lastIndexOf(".")).toLowerCase().trim();
//
//            StringBuilder sb = new StringBuilder();
//
//            sb.append(Environment.getExternalStorageDirectory()
//                    + File.separator
//                    + Environment.DIRECTORY_DCIM
//                    + File.separator
//                    + "Camera"
//                    + File.separator);
//            sb.append(MD5Utils.encrypt(s.getBytes("utf-8")));
//            sb.append(sfx);
//
//            Log.e("==============>", sb.toString());
//            File file = new File(sb.toString());
//            file.getParentFile().mkdirs();
//            if (file.exists()) file.delete();
//            return file;
//        });
//        Flowable.zip(a, b, (body, file) -> {
//            Log.e("zip==", Thread.currentThread().getName());
//            AMUtils.writeData2File(body.byteStream(), file, -1);
//            AlbumNotifyHelper.insertImageToMediaStore(context, file.getAbsolutePath(), 0);
//            AlbumNotifyHelper.notifyScanDcim(context, file.getAbsolutePath());
//            return false;
//        }).subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(body -> {
//                    Log.e("1==", Thread.currentThread().getName());
//                }, throwable -> {
//                    Log.e("2==", Thread.currentThread().getName());
//                    WaitUI.Cancel();
//                    throwable.printStackTrace();
//                }, () -> {
//                    Log.e("3==", Thread.currentThread().getName());
//                    WaitUI.Cancel();
//                    Toast.makeText(context, "图片保存成功", Toast.LENGTH_SHORT).show();
//                });
//    }
//
//    private void downVidio(String url) {
//        HttpAction.getInstance().get(url)
//                .observeOn(Schedulers.computation()) // 用于计算任务
//                .doOnNext(body -> {
//                    StringBuilder sb = new StringBuilder();
//
//                    sb.append(Environment.getExternalStorageDirectory()
//                            + File.separator
//                            + Environment.DIRECTORY_DCIM
//                            + File.separator
//                            + "Camera"
//                            + File.separator);
//                    sb.append(MD5Utils.encrypt(url.getBytes("utf-8")));
//                    sb.append(".mp4");
//
//                    Log.e("==============>", sb.toString());
//                    File file = new File(sb.toString());
//                    file.getParentFile().mkdirs();
//                    if (file.exists()) file.delete();
//
//                    AMUtils.writeData2File(body.byteStream(), file, -1);
//
//
//                    AlbumNotifyHelper.insertVideoToMediaStore(context, sb.toString(), 0, 5000);
//                    AlbumNotifyHelper.notifyScanDcim(context, sb.toString());
//
//                }).observeOn(AndroidSchedulers.mainThread())
//                .subscribe(body -> {
//                    WaitUI.Cancel();
//                    Toast.makeText(context, "视频保存成功", Toast.LENGTH_SHORT).show();
//                }, throwable -> {
//                    WaitUI.Cancel();
//                    throwable.printStackTrace();
//                });
//    }
//
//    private void share(boolean isFriend) {
//
//        SelectBusinessSchoolLeaderListResponse.DataBean.ListBean.BusinessSchoolLeaderBean leader = vo.getBusinessSchoolLeader();
//
//        //获取剪贴板管理器：
//        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
//        // 创建普通字符型ClipData
//        ClipData mClipData = ClipData.newPlainText("Label", leader.getInfo());
//        // 将ClipData内容放到系统剪贴板里。
//        cm.setPrimaryClip(mClipData);
//
//        Toast.makeText(context, "文字内容已复制到剪贴板", Toast.LENGTH_SHORT).show();
//
////                            articleClass;//分类（0链接1视频2图文）
//        int type = Integer.parseInt(leader.getArticleClass());
//        switch (type) {
//            case 0: {
//                String url = ConstantData.HOST + ConstantData.VIEWBOOK_DETAILS_URL + leader.getArticleId();
//                shareWebByWx(url, leader.getArticleThumbnail(), leader.getTitle(), isFriend);
//            }
//            break;
//            case 1: {
//                shareVideoByWx(leader.getVideoUrl(), leader.getArticleThumbnail(), leader.getTitle(), isFriend);
//            }
//            break;
//            case 2: {
//                List<String> list = vo.getBusinessSchoolImgList();
//                if (list == null || list.isEmpty()) {// 无图
//                    WechatUtils.getInstance().initWechatUtils(context).shareText(leader.getTitle(), isFriend ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline);
//                } else if (list.size() == 1) {
//                    shareImgByWx(list.get(0), leader.getTitle(), isFriend);
//                } else {   //多图
//                    viewTaskInfoImages(list, isFriend);
//                }
//            }
//            break;
//        }
//    }
//
//
//    //下载图片到本地
//    private void shareWebByWx(String videoUrl, String url, String title, boolean b) {
//        Observable.just(url)
//                .map((String imageUrl) -> {
//                            Bitmap bitmap = Glide.with(context).load(imageUrl).asBitmap().diskCacheStrategy(DiskCacheStrategy.NONE).into(50, 50).get();
//                            byte[] bytes = WechatUtils.bmpToByteArray(bitmap, true);
//                            return bytes;
//                        }
//                )
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe((bytes) -> {
//                            WechatUtils.getInstance().initWechatUtils(context).sendWebPage(videoUrl, bytes, title, "", b ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline);
//                        }
//                        , (throwable) -> throwable.printStackTrace()
//                );
//    }
//
//    private void shareVideoByWx(String videoUrl, String url, String title, boolean b) {
//        Observable.just(url)
//                .map((String imageUrl) -> {
//                            Bitmap bitmap = Glide.with(context).load(imageUrl).asBitmap().diskCacheStrategy(DiskCacheStrategy.NONE)
//                                    .into(50, 50)
//                                    .get();
//                            byte[] bytes = WechatUtils.bmpToByteArray(bitmap, true);
//                            return bytes;
//                        }
//                )
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe((bytes) -> {
//                            WechatUtils.getInstance().initWechatUtils(context).shareVideo(videoUrl, bytes, title, b ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline);
//                        }
//                        , (throwable) -> throwable.printStackTrace()
//                );
//    }
//
//    private void shareImgByWx(String url, String title, boolean b) {
//        Observable.just(url)
//                .map((String imageUrl) -> {
//                            Bitmap bitmap = Glide.with(context).load(imageUrl).asBitmap()
//                                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
//                                    .get();
//                            return bitmap;
//                        }
//                )
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe((bitmap) -> {
//                            WechatUtils.getInstance().initWechatUtils(context).shareImages(bitmap, title, b ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline);
//                        }
//                        , (throwable) -> throwable.printStackTrace()
//                );
//    }
//
//    private void viewTaskInfoImages(List<String> list, boolean b) {
//        ArrayList<Uri> uriList = new ArrayList<>();
//        Flowable.fromIterable(list)
//                .map((String imageUrl) -> {
//
//                            Bitmap bitmap = Glide.with(context).load(imageUrl).asBitmap()
//                                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
//                                    .get();
//                            StringBuilder sb = new StringBuilder();
//                            sb.append(android.os.Environment.getExternalStorageDirectory().getAbsolutePath());
//                            sb.append(ConstantData.NAME);
//                            sb.append("download_imgs/");
//                            sb.append(MD5Utils.encrypt(imageUrl.getBytes("utf-8")));
//                            sb.append(".jpg");
//
//                            File file = new File(sb.toString());
//                            file.getParentFile().mkdirs();
//                            if (file.exists()) file.delete();
//
//                            FileUtil.getInstance().writeFile(bitmap, file);
//                            return file;
//                        }
//                )
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe((file) -> {
//                            Uri uri = Uri.fromFile(file);
//                            uriList.add(uri);
//                        }
//                        , (throwable) -> throwable.printStackTrace()
//                        , () -> shareweipyqSomeImg(uriList, b));
//    }
//
//    //漏洞微信分享多图
//    private void shareweipyqSomeImg(ArrayList<Uri> imageUris, boolean isFriend) {
//        Intent shareIntent = new Intent();
//        //1调用系统分析
////        shareIntent.putExtra(Intent.EXTRA_TEXT, "123123123123");//正常使用的文字
////        shareIntent.putExtra("Kdescription", "DDDDD");
//        shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
//        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        //2添加图片数组
//        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
//        shareIntent.setType("image/*");
//        //3指定选择微信
//        ComponentName componentName = new ComponentName("com.tencent.mm", isFriend ? "com.tencent.mm.ui.tools.ShareImgUI" : "com.tencent.mm.ui.tools.ShareToTimeLineUI");
//
//
//        shareIntent.setComponent(componentName);
//        //4开始分享
//        context.startActivity(Intent.createChooser(shareIntent, "分享图片"));
//    }
//
//    public void setData(SelectBusinessSchoolLeaderListResponse.DataBean.ListBean vo, Object... objects) {
//        this.vo = vo;
//        this.objects = objects;
//    }
//
//
//}
