package com.winsion.wisdomstation.media.adapter;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.winsion.wisdomstation.R;
import com.winsion.wisdomstation.media.constants.FileStatus;
import com.winsion.wisdomstation.media.constants.FileType;
import com.winsion.wisdomstation.media.entity.RecordEntity;
import com.winsion.wisdomstation.utils.FileUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by wyl on 2017/6/22
 */
public class RecordAdapter extends CommonAdapter<RecordEntity> {

    public RecordAdapter(Context context, List<RecordEntity> data) {
        super(context, R.layout.item_record, data);
    }

    @Override
    protected void convert(ViewHolder viewHolder, RecordEntity recordEntity, int position) {
        if (recordEntity.getFileType() != FileType.TEXT) {
            viewHolder.setVisible(R.id.ll_media, true);
            viewHolder.setVisible(R.id.tv_note, false);
            convertMediaData(viewHolder, recordEntity);
        } else {
            viewHolder.setVisible(R.id.ll_media, false);
            viewHolder.setVisible(R.id.tv_note, true);
            convertNoteData(viewHolder, recordEntity);
        }
    }

    private void convertMediaData(ViewHolder viewHolder, RecordEntity recordEntity) {
        switch (recordEntity.getFileType()) {
            // 图片
            case FileType.PICTURE:
                viewHolder.setImageResource(R.id.iv_file_type, R.drawable.ic_picture);
                break;
            // 视频
            case FileType.VIDEO:
                viewHolder.setImageResource(R.id.iv_file_type, R.drawable.ic_video);
                break;
            // 音频
            case FileType.AUDIO:
                viewHolder.setImageResource(R.id.iv_file_type, R.drawable.ic_audio);
                break;
        }

        viewHolder.setText(R.id.tv_file_name, recordEntity.getFileName());

        switch (recordEntity.getFileStatus()) {
            case FileStatus.NO_UPLOAD:
            case FileStatus.NO_DOWNLOAD:
                viewHolder.setVisible(R.id.iv_status, true);
                viewHolder.setVisible(R.id.cpb_status, false);
                viewHolder.setImageResource(R.id.iv_status, R.drawable.ic_failed);
                break;
            case FileStatus.UPLOADING:
            case FileStatus.DOWNLOADING:
                viewHolder.setVisible(R.id.iv_status, false);
                viewHolder.setVisible(R.id.cpb_status, true);
                viewHolder.setProgress(R.id.cpb_status, recordEntity.getProgress());
                break;
            case FileStatus.SYNCHRONIZED:
                viewHolder.setVisible(R.id.iv_status, true);
                viewHolder.setVisible(R.id.cpb_status, false);
                viewHolder.setImageResource(R.id.iv_status, R.drawable.ic_synchronized);
                break;
        }
    }

    private void convertNoteData(ViewHolder viewHolder, RecordEntity recordEntity) {
        int status = recordEntity.getFileStatus();
        String note;
        if (status == FileStatus.NO_UPLOAD || status == FileStatus.SYNCHRONIZED) {
            note = FileUtils.readFile2String(recordEntity.getLocalPath(), "UTF-8");
        } else {
            note = "点击查看";
        }
        ForegroundColorSpan gray = new ForegroundColorSpan(0xFF69696D);

        SpannableStringBuilder builder = new SpannableStringBuilder(mContext.getString(R.string.note) + note);

        builder.setSpan(gray, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        TextView tvNote = viewHolder.getView(R.id.tv_note);
        tvNote.setText(builder);
    }
}
