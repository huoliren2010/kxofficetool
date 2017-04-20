package com.kx.officetool.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;

public class IntentUtil {
    public static Intent getCropIntent(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");

        intent.putExtra("crop", "true");

        intent.putExtra("aspectX", 1);

        intent.putExtra("aspectY", 1);

        intent.putExtra("outputX", 120);

        intent.putExtra("outputY", 120);

        intent.putExtra("scale", true);

        intent.putExtra("return-data", false);

        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        intent.putExtra("noFaceDetection", true); // no face detection

        return intent;
    }
}
