package playlistService;

import java.util.List;

import bean.BoardBean;
import bean.PlaylistBean;

public class PlaylistArrayView {
	private List<PlaylistBean> playlistArray;  //�Խù� ���

	public List<PlaylistBean> getPlaylistArray() {
		return playlistArray;
	}

	public PlaylistArrayView(List<PlaylistBean> playlistArray){
		this.playlistArray = playlistArray;
	}

}
