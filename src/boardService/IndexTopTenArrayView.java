package boardService;

import java.util.List;

import bean.BoardBean;

public class IndexTopTenArrayView {
	private List<BoardBean> toptenArray;	//°Ô½Ã¹° top10 List
	
	public List<BoardBean> getTopTenArray(){
		return toptenArray;
	}
	
	public IndexTopTenArrayView(List<BoardBean> toptenArray){
		this.toptenArray = toptenArray;
	}
}
