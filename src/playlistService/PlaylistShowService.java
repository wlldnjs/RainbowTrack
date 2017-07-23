package playlistService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.PlaylistBean;
import dao.PlaylistDao;

public class PlaylistShowService implements PlaylistService {

	private static PlaylistShowService instance;

	public static PlaylistShowService getInstance() {
		if (instance == null) {
			instance = new PlaylistShowService();
		}
		return instance;
	}

	public PlaylistShowService() { }

	public PlaylistArrayView PlaylistShowService(String user_id) {

		PlaylistDao playlistDao = PlaylistDao.getInstance();
		List<PlaylistBean> playlistArray = null;
		playlistArray = playlistDao.selectPlaylist(user_id);
		
		return new PlaylistArrayView(playlistArray);

	}

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) {

		return null;
	}

}
