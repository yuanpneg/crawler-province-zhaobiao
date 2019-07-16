package pojo1;

import java.util.List;

public class JavaBean {


    private int total;
    private int page;
    private int records;
    private int pagerecords;
    private String costtime;
    private List<RowsBean> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRecords() {
        return records;
    }

    public void setRecords(int records) {
        this.records = records;
    }

    public int getPagerecords() {
        return pagerecords;
    }

    public void setPagerecords(int pagerecords) {
        this.pagerecords = pagerecords;
    }

    public String getCosttime() {
        return costtime;
    }

    public void setCosttime(String costtime) {
        this.costtime = costtime;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {

        private int rownum;
        private String bulletinid;
        private String pushdocid;
        private String bulletintitle;
        private Object projectid;
        private String projectcode;
        private Object pname;
        private int bulletinidtype;
        private String bulletinidcontent;
        private int status;
        private String auditopinion;
        private int projecttype;
        private String createdate;
        private String createuserid;
        private String createusername;
        private String modifydate;
        private String modifyuserid;
        private String modifyusername;
        private String companyid;
        private Object buildid;
        private String projectname;
        private int is_xj;
        private int isupload;
        private String old_gg_id;
        private Object old_gg_name;
        private int jjgg_type;
        private Object transaction_amount;
        private String winner;
        private Object gzgg_type;
        private Object kbsj;
        private Object biaohao;
        private Object publicitydays;

        public int getRownum() {
            return rownum;
        }

        public void setRownum(int rownum) {
            this.rownum = rownum;
        }

        public String getBulletinid() {
            return bulletinid;
        }

        public void setBulletinid(String bulletinid) {
            this.bulletinid = bulletinid;
        }

        public String getPushdocid() {
            return pushdocid;
        }

        public void setPushdocid(String pushdocid) {
            this.pushdocid = pushdocid;
        }

        public String getBulletintitle() {
            return bulletintitle;
        }

        public void setBulletintitle(String bulletintitle) {
            this.bulletintitle = bulletintitle;
        }

        public Object getProjectid() {
            return projectid;
        }

        public void setProjectid(Object projectid) {
            this.projectid = projectid;
        }

        public String getProjectcode() {
            return projectcode;
        }

        public void setProjectcode(String projectcode) {
            this.projectcode = projectcode;
        }

        public Object getPname() {
            return pname;
        }

        public void setPname(Object pname) {
            this.pname = pname;
        }

        public int getBulletinidtype() {
            return bulletinidtype;
        }

        public void setBulletinidtype(int bulletinidtype) {
            this.bulletinidtype = bulletinidtype;
        }

        public String getBulletinidcontent() {
            return bulletinidcontent;
        }

        public void setBulletinidcontent(String bulletinidcontent) {
            this.bulletinidcontent = bulletinidcontent;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getAuditopinion() {
            return auditopinion;
        }

        public void setAuditopinion(String auditopinion) {
            this.auditopinion = auditopinion;
        }

        public int getProjecttype() {
            return projecttype;
        }

        public void setProjecttype(int projecttype) {
            this.projecttype = projecttype;
        }

        public String getCreatedate() {
            return createdate;
        }

        public void setCreatedate(String createdate) {
            this.createdate = createdate;
        }

        public String getCreateuserid() {
            return createuserid;
        }

        public void setCreateuserid(String createuserid) {
            this.createuserid = createuserid;
        }

        public String getCreateusername() {
            return createusername;
        }

        public void setCreateusername(String createusername) {
            this.createusername = createusername;
        }

        public String getModifydate() {
            return modifydate;
        }

        public void setModifydate(String modifydate) {
            this.modifydate = modifydate;
        }

        public String getModifyuserid() {
            return modifyuserid;
        }

        public void setModifyuserid(String modifyuserid) {
            this.modifyuserid = modifyuserid;
        }

        public String getModifyusername() {
            return modifyusername;
        }

        public void setModifyusername(String modifyusername) {
            this.modifyusername = modifyusername;
        }

        public String getCompanyid() {
            return companyid;
        }

        public void setCompanyid(String companyid) {
            this.companyid = companyid;
        }

        public Object getBuildid() {
            return buildid;
        }

        public void setBuildid(Object buildid) {
            this.buildid = buildid;
        }

        public String getProjectname() {
            return projectname;
        }

        public void setProjectname(String projectname) {
            this.projectname = projectname;
        }

        public int getIs_xj() {
            return is_xj;
        }

        public void setIs_xj(int is_xj) {
            this.is_xj = is_xj;
        }

        public int getIsupload() {
            return isupload;
        }

        public void setIsupload(int isupload) {
            this.isupload = isupload;
        }

        public String getOld_gg_id() {
            return old_gg_id;
        }

        public void setOld_gg_id(String old_gg_id) {
            this.old_gg_id = old_gg_id;
        }

        public Object getOld_gg_name() {
            return old_gg_name;
        }

        public void setOld_gg_name(Object old_gg_name) {
            this.old_gg_name = old_gg_name;
        }

        public int getJjgg_type() {
            return jjgg_type;
        }

        public void setJjgg_type(int jjgg_type) {
            this.jjgg_type = jjgg_type;
        }

        public Object getTransaction_amount() {
            return transaction_amount;
        }

        public void setTransaction_amount(Object transaction_amount) {
            this.transaction_amount = transaction_amount;
        }

        public String getWinner() {
            return winner;
        }

        public void setWinner(String winner) {
            this.winner = winner;
        }

        public Object getGzgg_type() {
            return gzgg_type;
        }

        public void setGzgg_type(Object gzgg_type) {
            this.gzgg_type = gzgg_type;
        }

        public Object getKbsj() {
            return kbsj;
        }

        public void setKbsj(Object kbsj) {
            this.kbsj = kbsj;
        }

        public Object getBiaohao() {
            return biaohao;
        }

        public void setBiaohao(Object biaohao) {
            this.biaohao = biaohao;
        }

        public Object getPublicitydays() {
            return publicitydays;
        }

        public void setPublicitydays(Object publicitydays) {
            this.publicitydays = publicitydays;
        }
    }
}
