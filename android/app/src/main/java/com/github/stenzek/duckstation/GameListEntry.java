package com.github.stenzek.duckstation;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

public class GameListEntry {
    public enum EntryType
    {
        Disc,
        PSExe
    }

    private String mPath;
    private String mCode;
    private String mTitle;
    private ConsoleRegion mRegion;
    private EntryType mType;
    private long mSize;

    public GameListEntry(String path, String code, String title, String region,
                         String type, long size) {
        mPath = path;
        mCode = code;
        mTitle = title;
        mSize = size;

        try {
            mRegion = ConsoleRegion.valueOf(region);
        } catch (IllegalArgumentException e) {
            mRegion = ConsoleRegion.NTSC_U;
        }

        try {
            mType = EntryType.valueOf(type);
        } catch (IllegalArgumentException e) {
            mType = EntryType.Disc;
        }
    }

    public String getPath() {
        return mPath;
    }

    public String getCode() {
        return mCode;
    }

    public String getTitle() {
        return mTitle;
    }

    public ConsoleRegion getRegion() {
        return mRegion;
    }

    public EntryType getType() { return mType; }

    public void fillView(View view) {
        ((TextView) view.findViewById(R.id.game_list_view_entry_title)).setText(mTitle);
        ((TextView) view.findViewById(R.id.game_list_view_entry_path)).setText(mPath);

        String sizeString = String.format("%.2f MB", (double) mSize / 1048576.0);
        ((TextView) view.findViewById(R.id.game_list_view_entry_size)).setText(sizeString);

        int regionDrawableId;
        switch (mRegion) {
            case NTSC_J:
                regionDrawableId = R.drawable.flag_jp;
                break;
            case NTSC_U:
            default:
                regionDrawableId = R.drawable.flag_us;
                break;
            case PAL:
                regionDrawableId = R.drawable.flag_eu;
                break;
        }

        ((ImageView) view.findViewById(R.id.game_list_view_entry_region_icon))
                .setImageDrawable(ContextCompat.getDrawable(view.getContext(), regionDrawableId));

        int typeDrawableId;
        switch (mType) {
            case Disc:
            default:
                typeDrawableId = R.drawable.ic_media_cdrom;
                break;

            case PSExe:
                typeDrawableId = R.drawable.ic_emblem_system;
                break;
        }

        ((ImageView) view.findViewById(R.id.game_list_view_entry_type_icon))
                .setImageDrawable(ContextCompat.getDrawable(view.getContext(), typeDrawableId));
    }
}
