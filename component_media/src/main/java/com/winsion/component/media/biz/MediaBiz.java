package com.winsion.component.media.biz;

import android.support.annotation.IntDef;

import com.winsion.component.basic.utils.constants.Formatter;
import com.winsion.component.media.constants.FileType;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Date;

/**
 * Created by 10295 on 2018/3/12.
 * biz
 */

public class MediaBiz {
    public static File getMediaFile(File file, @FileTypeLimit int type) {
        if (file.exists() || file.mkdirs()) {
            String timeStamp = Formatter.DATE_FORMAT11.format(new Date());
            File mediaFile;
            if (type == FileType.PICTURE) {
                mediaFile = new File(file.getPath() + File.separator
                        + "IMG_" + timeStamp + ".jpg");
            } else if (type == FileType.VIDEO) {
                mediaFile = new File(file.getPath() + File.separator
                        + "VID_" + timeStamp + ".mp4");
            } else if (type == FileType.AUDIO) {
                mediaFile = new File(file.getPath() + File.separator
                        + "VOI_" + timeStamp + ".aac");
            } else if (type == FileType.TEXT) {
                mediaFile = new File(file.getPath() + File.separator
                        + "TEXT_NOTE.txt");
            } else {
                return null;
            }
            return mediaFile;
        }
        return null;
    }

    /**
     * 文件类型
     */
    @IntDef({FileType.PICTURE, FileType.VIDEO, FileType.AUDIO, FileType.TEXT})
    @Retention(RetentionPolicy.SOURCE)
    @interface FileTypeLimit {
    }
}