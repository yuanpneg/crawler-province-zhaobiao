package pojo1;

public class File {
    private String filetitle;
    private String fileurl;
    private String filetype;

    public File() {
    }

    public File(String filetitle, String fileurl, String filetype) {
        this.filetitle = filetitle;
        this.fileurl = fileurl;
        this.filetype = filetype;
    }

    public String getFiletitle() {
        return filetitle;
    }

    public void setFiletitle(String filetitle) {
        this.filetitle = filetitle;
    }

    public String getFileurl() {
        return fileurl;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }
}
