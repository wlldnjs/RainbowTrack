package util;
import org.apache.commons.fileupload.disk.*;
import org.apache.commons.fileupload.servlet.*;
import org.apache.commons.fileupload.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.*;
public class MultiPart {
    List items;  // �Է� ������ �׸��� ������ List
    // ������
    public MultiPart(HttpServletRequest request) throws Exception {
        FileItemFactory factory = new DiskFileItemFactory(); // ���� ���ε� �ڵ鷯 ����
        ServletFileUpload upload = new ServletFileUpload(factory);  // ���� ���ε� �ڵ鷯 ���� 
        items = upload.parseRequest(request);
    }
    // �־��� �̸��� �ش��ϴ� ������ ���� �����ϴ� �޼���
    public String getParameter(String fieldName) throws UnsupportedEncodingException {    	
        for (int cnt = 0; cnt < items.size(); cnt++) {
            FileItem item = (FileItem) items.get(cnt);
            if (item.getFieldName().equals(fieldName)){            	
                return item.getString("UTF-8");        
            }
        }
        return null;
    }
    // �־��� �̸��� �ش��ϴ� ���ε� ������ ��θ��� �����ϴ� �޼���
    public String getFilePath(String fieldName) throws UnsupportedEncodingException {
        for (int cnt = 0; cnt < items.size(); cnt++) {
            FileItem item = (FileItem) items.get(cnt);
            if (item.getFieldName().equals(fieldName)){            	
                return item.getName();            
            }
        }
        return null;
    }
    // �־��� �̸��� �ش��ϴ� ���ε� ������ �̸��� �����ϴ� �޼���
    public String getFileName(String fieldName) throws UnsupportedEncodingException {
    	
        String path = getFilePath(fieldName);  
        int index1 = path.lastIndexOf("/");
        int index2 = path.lastIndexOf("\\");
        int index = 0;      
        
        
        if (index1 > index2)
            index = index1;
        else
            index = index2;
        
        
        if (index < 0)
            return path;
        else
            return path.substring(index + 1);  // ���ϸ�.Ȯ���
    }
    // �־��� �̸��� �ش��ϴ� ���ε� ������ �����ϴ� �޼���
    public void saveFile(String fieldName, String path) throws Exception {
        for (int cnt = 0; cnt < items.size(); cnt++) {
            FileItem item = (FileItem) items.get(cnt);
            if (item.getFieldName().equals(fieldName)) {            	
                if (!item.isFormField()) {  
                  item.write(new File(path));
                }
            }
        }
    }
}    
