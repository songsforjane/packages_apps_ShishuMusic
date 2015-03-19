package com.kabouzeid.gramophone.adapter;

import android.app.Activity;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.kabouzeid.gramophone.R;
import com.kabouzeid.gramophone.helper.DeletePlaylistDialogHelper;
import com.kabouzeid.gramophone.helper.RenamePlaylistDialogHelper;
import com.kabouzeid.gramophone.model.Playlist;
import com.kabouzeid.gramophone.ui.activities.base.AbsFabActivity;
import com.kabouzeid.gramophone.util.NavigationUtil;

import java.util.List;

/**
 * Created by karim on 16.03.15.
 */
public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ViewHolder> {
    public static final String TAG = PlaylistAdapter.class.getSimpleName();
    protected Activity activity;
    protected List<Playlist> dataSet;

    public PlaylistAdapter(Activity activity, List<Playlist> objects) {
        this.activity = activity;
        dataSet = objects;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_list_playlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.playlistName.setText(dataSet.get(position).name);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView playlistName;
        private ImageView menu;

        public ViewHolder(View itemView) {
            super(itemView);
            playlistName = (TextView) itemView.findViewById(R.id.playlist_name);
            menu = (ImageView) itemView.findViewById(R.id.menu);
            itemView.setOnClickListener(this);
            menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(activity, view);
                    popupMenu.inflate(R.menu.menu_item_playlist);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.action_rename_playlist:
                                    RenamePlaylistDialogHelper.getDialog(activity, dataSet.get(getAdapterPosition()).id).show();
                                    return true;
                                case R.id.action_delete_playlist:
                                    DeletePlaylistDialogHelper.getDialog(activity, dataSet.get(getAdapterPosition()).id).show();
                                    return true;
                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                }
            });
        }

        @Override
        public void onClick(View view) {
            Pair[] sharedViews = null;
            if (activity instanceof AbsFabActivity)
                sharedViews = ((AbsFabActivity) activity).getSharedViewsWithFab(sharedViews);
            NavigationUtil.goToPlaylist(activity, dataSet.get(getAdapterPosition()).id, sharedViews);
        }
    }
}