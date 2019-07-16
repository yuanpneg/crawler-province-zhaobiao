package bean;

import java.util.List;

public class ShanghaiJson {

    /**
     * status : SUCCESS
     * result : {"pageNum":1,"pageSize":10,"size":10,"startRow":1,"endRow":10,"total":123,"pages":13,"list":[{"tenderProjectCode":"e3100000151020891001","projectName":"松江区长谷路（玉树路-永航路）中修工程","source":null,"tenderContent":"施工招标","time":"2018-08-26 14:22:14","bulletinStatus":1,"evaluationStatus":0,"openRecordStatus":0,"qualificationResultStatus":0,"winnerStatus":0,"title":null},{"tenderProjectCode":"e3100000151019437003","projectName":"临港物流园区雨水排放口生态化治理工程改建","source":null,"tenderContent":"施工招标","time":"2018-08-26 10:52:25","bulletinStatus":1,"evaluationStatus":0,"openRecordStatus":0,"qualificationResultStatus":0,"winnerStatus":0,"title":null},{"tenderProjectCode":"e3100000151019438003","projectName":"临港物流园区道路海绵化改造一期工程改建","source":null,"tenderContent":"施工招标","time":"2018-08-26 10:41:54","bulletinStatus":1,"evaluationStatus":0,"openRecordStatus":0,"qualificationResultStatus":0,"winnerStatus":0,"title":null},{"tenderProjectCode":"e3100000151020890001","projectName":"奉贤区2018年水系沟通工程（西片区）","source":null,"tenderContent":"勘察设计一体化招标","time":"2018-08-25 01:11:32","bulletinStatus":1,"evaluationStatus":0,"openRecordStatus":0,"qualificationResultStatus":0,"winnerStatus":0,"title":null},{"tenderProjectCode":"e3100000151016467003","projectName":"上大路南侧祁连山路东侧地块文体服务中心","source":null,"tenderContent":"施工招标","time":"2018-08-24 23:14:32","bulletinStatus":1,"evaluationStatus":0,"openRecordStatus":0,"qualificationResultStatus":0,"winnerStatus":0,"title":null},{"tenderProjectCode":"e3100000151020889001","projectName":"新建商业用房","source":null,"tenderContent":"施工招标","time":"2018-08-24 23:13:27","bulletinStatus":1,"evaluationStatus":0,"openRecordStatus":0,"qualificationResultStatus":0,"winnerStatus":0,"title":null},{"tenderProjectCode":"e3100000151020862001","projectName":"长江南路188弄等27家驻区部队、消防队截污纳管工程","source":null,"tenderContent":"勘察设计施工一体化招标","time":"2018-08-24 21:54:38","bulletinStatus":1,"evaluationStatus":0,"openRecordStatus":0,"qualificationResultStatus":0,"winnerStatus":0,"title":null},{"tenderProjectCode":"e3100000151020089002","projectName":"顾村镇N12-1101单元04-02地块动迁安置房项目","source":null,"tenderContent":"施工招标","time":"2018-08-24 20:45:20","bulletinStatus":1,"evaluationStatus":0,"openRecordStatus":0,"qualificationResultStatus":0,"winnerStatus":0,"title":null},{"tenderProjectCode":"e3100000151020886001","projectName":"青浦区徐泾北大型社区27-04地块项目","source":null,"tenderContent":"设计招标","time":"2018-08-24 20:26:46","bulletinStatus":1,"evaluationStatus":0,"openRecordStatus":0,"qualificationResultStatus":0,"winnerStatus":0,"title":null},{"tenderProjectCode":"e3100000151020842001","projectName":"青浦区夏阳街道村居公共区域生态修复项目","source":null,"tenderContent":"施工招标","time":"2018-08-24 20:23:41","bulletinStatus":1,"evaluationStatus":0,"openRecordStatus":0,"qualificationResultStatus":0,"winnerStatus":0,"title":null}],"prePage":0,"nextPage":2,"isFirstPage":true,"isLastPage":false,"hasPreviousPage":false,"hasNextPage":true,"navigatePages":8,"navigatepageNums":[1,2,3,4,5,6,7,8],"navigateFirstPage":1,"navigateLastPage":8,"firstPage":1,"lastPage":8}
     * info : 调用成功
     */

    private String status;
    private ResultBean result;
    private String info;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public static class ResultBean {
        /**
         * pageNum : 1
         * pageSize : 10
         * size : 10
         * startRow : 1
         * endRow : 10
         * total : 123
         * pages : 13
         * list : [{"tenderProjectCode":"e3100000151020891001","projectName":"松江区长谷路（玉树路-永航路）中修工程","source":null,"tenderContent":"施工招标","time":"2018-08-26 14:22:14","bulletinStatus":1,"evaluationStatus":0,"openRecordStatus":0,"qualificationResultStatus":0,"winnerStatus":0,"title":null},{"tenderProjectCode":"e3100000151019437003","projectName":"临港物流园区雨水排放口生态化治理工程改建","source":null,"tenderContent":"施工招标","time":"2018-08-26 10:52:25","bulletinStatus":1,"evaluationStatus":0,"openRecordStatus":0,"qualificationResultStatus":0,"winnerStatus":0,"title":null},{"tenderProjectCode":"e3100000151019438003","projectName":"临港物流园区道路海绵化改造一期工程改建","source":null,"tenderContent":"施工招标","time":"2018-08-26 10:41:54","bulletinStatus":1,"evaluationStatus":0,"openRecordStatus":0,"qualificationResultStatus":0,"winnerStatus":0,"title":null},{"tenderProjectCode":"e3100000151020890001","projectName":"奉贤区2018年水系沟通工程（西片区）","source":null,"tenderContent":"勘察设计一体化招标","time":"2018-08-25 01:11:32","bulletinStatus":1,"evaluationStatus":0,"openRecordStatus":0,"qualificationResultStatus":0,"winnerStatus":0,"title":null},{"tenderProjectCode":"e3100000151016467003","projectName":"上大路南侧祁连山路东侧地块文体服务中心","source":null,"tenderContent":"施工招标","time":"2018-08-24 23:14:32","bulletinStatus":1,"evaluationStatus":0,"openRecordStatus":0,"qualificationResultStatus":0,"winnerStatus":0,"title":null},{"tenderProjectCode":"e3100000151020889001","projectName":"新建商业用房","source":null,"tenderContent":"施工招标","time":"2018-08-24 23:13:27","bulletinStatus":1,"evaluationStatus":0,"openRecordStatus":0,"qualificationResultStatus":0,"winnerStatus":0,"title":null},{"tenderProjectCode":"e3100000151020862001","projectName":"长江南路188弄等27家驻区部队、消防队截污纳管工程","source":null,"tenderContent":"勘察设计施工一体化招标","time":"2018-08-24 21:54:38","bulletinStatus":1,"evaluationStatus":0,"openRecordStatus":0,"qualificationResultStatus":0,"winnerStatus":0,"title":null},{"tenderProjectCode":"e3100000151020089002","projectName":"顾村镇N12-1101单元04-02地块动迁安置房项目","source":null,"tenderContent":"施工招标","time":"2018-08-24 20:45:20","bulletinStatus":1,"evaluationStatus":0,"openRecordStatus":0,"qualificationResultStatus":0,"winnerStatus":0,"title":null},{"tenderProjectCode":"e3100000151020886001","projectName":"青浦区徐泾北大型社区27-04地块项目","source":null,"tenderContent":"设计招标","time":"2018-08-24 20:26:46","bulletinStatus":1,"evaluationStatus":0,"openRecordStatus":0,"qualificationResultStatus":0,"winnerStatus":0,"title":null},{"tenderProjectCode":"e3100000151020842001","projectName":"青浦区夏阳街道村居公共区域生态修复项目","source":null,"tenderContent":"施工招标","time":"2018-08-24 20:23:41","bulletinStatus":1,"evaluationStatus":0,"openRecordStatus":0,"qualificationResultStatus":0,"winnerStatus":0,"title":null}]
         * prePage : 0
         * nextPage : 2
         * isFirstPage : true
         * isLastPage : false
         * hasPreviousPage : false
         * hasNextPage : true
         * navigatePages : 8
         * navigatepageNums : [1,2,3,4,5,6,7,8]
         * navigateFirstPage : 1
         * navigateLastPage : 8
         * firstPage : 1
         * lastPage : 8
         */

        private int pageNum;
        private int pageSize;
        private int size;
        private int startRow;
        private int endRow;
        private int total;
        private int pages;
        private int prePage;
        private int nextPage;
        private boolean isFirstPage;
        private boolean isLastPage;
        private boolean hasPreviousPage;
        private boolean hasNextPage;
        private int navigatePages;
        private int navigateFirstPage;
        private int navigateLastPage;
        private int firstPage;
        private int lastPage;
        private List<ListBean> list;
        private List<Integer> navigatepageNums;

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getStartRow() {
            return startRow;
        }

        public void setStartRow(int startRow) {
            this.startRow = startRow;
        }

        public int getEndRow() {
            return endRow;
        }

        public void setEndRow(int endRow) {
            this.endRow = endRow;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public int getPrePage() {
            return prePage;
        }

        public void setPrePage(int prePage) {
            this.prePage = prePage;
        }

        public int getNextPage() {
            return nextPage;
        }

        public void setNextPage(int nextPage) {
            this.nextPage = nextPage;
        }

        public boolean isIsFirstPage() {
            return isFirstPage;
        }

        public void setIsFirstPage(boolean isFirstPage) {
            this.isFirstPage = isFirstPage;
        }

        public boolean isIsLastPage() {
            return isLastPage;
        }

        public void setIsLastPage(boolean isLastPage) {
            this.isLastPage = isLastPage;
        }

        public boolean isHasPreviousPage() {
            return hasPreviousPage;
        }

        public void setHasPreviousPage(boolean hasPreviousPage) {
            this.hasPreviousPage = hasPreviousPage;
        }

        public boolean isHasNextPage() {
            return hasNextPage;
        }

        public void setHasNextPage(boolean hasNextPage) {
            this.hasNextPage = hasNextPage;
        }

        public int getNavigatePages() {
            return navigatePages;
        }

        public void setNavigatePages(int navigatePages) {
            this.navigatePages = navigatePages;
        }

        public int getNavigateFirstPage() {
            return navigateFirstPage;
        }

        public void setNavigateFirstPage(int navigateFirstPage) {
            this.navigateFirstPage = navigateFirstPage;
        }

        public int getNavigateLastPage() {
            return navigateLastPage;
        }

        public void setNavigateLastPage(int navigateLastPage) {
            this.navigateLastPage = navigateLastPage;
        }

        public int getFirstPage() {
            return firstPage;
        }

        public void setFirstPage(int firstPage) {
            this.firstPage = firstPage;
        }

        public int getLastPage() {
            return lastPage;
        }

        public void setLastPage(int lastPage) {
            this.lastPage = lastPage;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public List<Integer> getNavigatepageNums() {
            return navigatepageNums;
        }

        public void setNavigatepageNums(List<Integer> navigatepageNums) {
            this.navigatepageNums = navigatepageNums;
        }

        public static class ListBean {
            /**
             * tenderProjectCode : e3100000151020891001
             * projectName : 松江区长谷路（玉树路-永航路）中修工程
             * source : null
             * tenderContent : 施工招标
             * time : 2018-08-26 14:22:14
             * bulletinStatus : 1
             * evaluationStatus : 0
             * openRecordStatus : 0
             * qualificationResultStatus : 0
             * winnerStatus : 0
             * title : null
             */

            private String tenderProjectCode;
            private String projectName;
            private Object source;
            private String tenderContent;
            private String time;
            private int bulletinStatus;
            private int evaluationStatus;
            private int openRecordStatus;
            private int qualificationResultStatus;
            private int winnerStatus;
            private Object title;

            public String getTenderProjectCode() {
                return tenderProjectCode;
            }

            public void setTenderProjectCode(String tenderProjectCode) {
                this.tenderProjectCode = tenderProjectCode;
            }

            public String getProjectName() {
                return projectName;
            }

            public void setProjectName(String projectName) {
                this.projectName = projectName;
            }

            public Object getSource() {
                return source;
            }

            public void setSource(Object source) {
                this.source = source;
            }

            public String getTenderContent() {
                return tenderContent;
            }

            public void setTenderContent(String tenderContent) {
                this.tenderContent = tenderContent;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public int getBulletinStatus() {
                return bulletinStatus;
            }

            public void setBulletinStatus(int bulletinStatus) {
                this.bulletinStatus = bulletinStatus;
            }

            public int getEvaluationStatus() {
                return evaluationStatus;
            }

            public void setEvaluationStatus(int evaluationStatus) {
                this.evaluationStatus = evaluationStatus;
            }

            public int getOpenRecordStatus() {
                return openRecordStatus;
            }

            public void setOpenRecordStatus(int openRecordStatus) {
                this.openRecordStatus = openRecordStatus;
            }

            public int getQualificationResultStatus() {
                return qualificationResultStatus;
            }

            public void setQualificationResultStatus(int qualificationResultStatus) {
                this.qualificationResultStatus = qualificationResultStatus;
            }

            public int getWinnerStatus() {
                return winnerStatus;
            }

            public void setWinnerStatus(int winnerStatus) {
                this.winnerStatus = winnerStatus;
            }

            public Object getTitle() {
                return title;
            }

            public void setTitle(Object title) {
                this.title = title;
            }
        }
    }
}
