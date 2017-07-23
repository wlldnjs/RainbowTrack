package playlistService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.PlaylistBean;
import dao.PlaylistDao;

public class PlaylistInsertService implements PlaylistService {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) {
		String viewPage="jsp/main/boardRead.bd";
		System.out.println("인서트 서비스 진입");
		String id = request.getParameter("id");
		int boardIdx = Integer.parseInt(request.getParameter("idx"));
		String musicName = request.getParameter("name");
		System.out.println(id + boardIdx + musicName);
		
		PlaylistBean bean = new PlaylistBean();
		bean.setId(id);
		bean.setBoard_idx(boardIdx);
		bean.setMusicName(musicName);
		
		PlaylistDao dao = new PlaylistDao();
		dao.insertPlaylist(bean);
		
		return viewPage;
	}

}
