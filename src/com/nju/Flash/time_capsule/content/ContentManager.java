package com.nju.Flash.time_capsule.content;

import android.view.LayoutInflater;
import android.widget.LinearLayout;
import com.nju.Flash.R;
import com.nju.Flash.time_capsule.TimeCapsuleActivity;

import java.util.ArrayList;

/**
 * For Flash
 *
 * @author 杨涛
 *         On 14-3-26 下午7:12 by IntelliJ IDEA
 */
public class ContentManager {
    private static ContentManager contentManager = null;
    private ArrayList<Content> contents = new ArrayList<Content>();
    private static TimeCapsuleActivity activity = null;
    private static LayoutInflater inflater = null;
    private LinearLayout contentLayout = null;

    private Content titleContent = null;
    private Content textContent = null;
    private Content timeContent = null;
    private Content recordContent = null;

    private ContentManager(TimeCapsuleActivity activity){
        ContentManager.activity = activity;
        inflater = activity.getInflater();
        contentLayout = (LinearLayout)activity.findViewById(R.id.time_capsule_detail_content);
        initializeContents();
        addContents();
    }

    private void initializeContents() {
        titleContent = new TitleContent(activity);
        textContent = new TextContent(activity);
        timeContent = new TimeContent(activity);
        recordContent = new RecordContent(activity);
    }

    private void addContents() {
        contents.add(titleContent);
        contents.add(textContent);
        contents.add(timeContent);
        contents.add(recordContent);
    }

    public void changeToContent(int flag){
        contentLayout.removeAllViews();
        contentLayout.addView(contents.get(flag).contentView);
    }

    public Content getContent(int flag){
        return contents.get(flag);
    }

    public static ContentManager getInstance(TimeCapsuleActivity activity){
        if(contentManager==null||ContentManager.activity!=activity)
            contentManager = new ContentManager(activity);
        return contentManager;
    }
}
