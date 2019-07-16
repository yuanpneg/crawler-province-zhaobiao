package bean;

import java.util.List;

public class GanziJson {

    /**
     * code : 0
     * msg : success
     * data : {"content":[{"id":"ffc62df5-783b-4001-a8e8-fce0beb9f87d","mctype":"石渠县司法局业务用房项目（随机发包）","mckeys":"2018-08-23","mcontent":null,"vtype":null,"pubtime":1534999425000,"time":null,"utime":null,"bunique":null,"rmenuid":null,"cmenumcode":"JYGCJS","ruserid":null,"areacode":"513332","fnum":null,"areaname":"石渠县","typecode":"ZBGG","state":null,"realpubtime":1534999425000,"cuername":null,"fname":null},{"id":"82ef1864-3f6f-49b7-879a-f9804c78cb5e","mctype":"甘孜县大德乡幼儿园建设项目。","mckeys":"2018-08-23","mcontent":null,"vtype":null,"pubtime":1534999416000,"time":null,"utime":null,"bunique":null,"rmenuid":null,"cmenumcode":"JYGCJS","ruserid":null,"areacode":"513328","fnum":null,"areaname":"甘孜县","typecode":"ZBGG","state":null,"realpubtime":1534999416000,"cuername":null,"fname":null},{"id":"9e3a2341-b963-4f5a-bc3b-60c598381e3d","mctype":"国道350线道孚县八美镇龙灯乡松林口至炉霍县城段大修工程 ","mckeys":"2018-08-23","mcontent":null,"vtype":null,"pubtime":1534999408000,"time":null,"utime":null,"bunique":null,"rmenuid":null,"cmenumcode":"JYGCJS","ruserid":null,"areacode":"513326","fnum":null,"areaname":"道孚县","typecode":"ZBGG","state":null,"realpubtime":1534999408000,"cuername":null,"fname":null},{"id":"31885f71-2c6f-4c6e-82a9-87c5e7e24b6c","mctype":"康定市疾病预防控制中心建设项目","mckeys":"2018-08-23","mcontent":null,"vtype":null,"pubtime":1534996431000,"time":null,"utime":null,"bunique":null,"rmenuid":null,"cmenumcode":"JYGCJS","ruserid":null,"areacode":"513321","fnum":null,"areaname":"康定县","typecode":"ZBGG","state":null,"realpubtime":1534996431000,"cuername":null,"fname":null},{"id":"3fcf7f20-0d8c-4d14-ad94-16c8ab6d4ed3","mctype":"色达县洛若镇智慧景区建设工程 〔第二次〕","mckeys":"2018-08-23","mcontent":null,"vtype":null,"pubtime":1534996425000,"time":null,"utime":null,"bunique":null,"rmenuid":null,"cmenumcode":"JYGCJS","ruserid":null,"areacode":"513333","fnum":null,"areaname":"色达县","typecode":"ZBGG","state":null,"realpubtime":1534996425000,"cuername":null,"fname":null},{"id":"d35eb244-11a8-4439-90b9-a4dbf809f3b2","mctype":"国道318线康定\u2014雅江段 \u201c交通+旅游\u201d 融合发展示范项目一期工程","mckeys":"2018-08-22","mcontent":null,"vtype":null,"pubtime":1534931528000,"time":null,"utime":null,"bunique":null,"rmenuid":null,"cmenumcode":"JYGCJS","ruserid":null,"areacode":"513321","fnum":null,"areaname":"康定县","typecode":"ZBGG","state":null,"realpubtime":1534931528000,"cuername":null,"fname":null},{"id":"f4756a60-674d-4f7a-947f-e4591f92d6a2","mctype":"炉霍县雅德乡交纳村\u201c最美藏家庭院、幸福美丽新村\u201d庭院改造建设项目","mckeys":"2018-08-22","mcontent":null,"vtype":null,"pubtime":1534931248000,"time":null,"utime":null,"bunique":null,"rmenuid":null,"cmenumcode":"JYGCJS","ruserid":null,"areacode":"513327","fnum":null,"areaname":"炉霍县","typecode":"ZBGG","state":null,"realpubtime":1534931248000,"cuername":null,"fname":null},{"id":"05f8e338-b64b-49a1-b8c7-e41298f7cff3","mctype":"巴塘县地巫乡四点坝村通畅工程监理","mckeys":"2018-08-22","mcontent":null,"vtype":null,"pubtime":1534928723000,"time":null,"utime":null,"bunique":null,"rmenuid":null,"cmenumcode":"JYGCJS","ruserid":null,"areacode":"513335","fnum":null,"areaname":"巴塘县","typecode":"ZBGG","state":null,"realpubtime":1534928723000,"cuername":null,"fname":null},{"id":"0bccd1da-186b-47d4-a15f-0c2cbca1c42b","mctype":"炉霍县乡镇干部职工周转房建设项目","mckeys":"2018-08-22","mcontent":null,"vtype":null,"pubtime":1534928686000,"time":null,"utime":null,"bunique":null,"rmenuid":null,"cmenumcode":"JYGCJS","ruserid":null,"areacode":"513327","fnum":null,"areaname":"炉霍县","typecode":"ZBGG","state":null,"realpubtime":1534928686000,"cuername":null,"fname":null},{"id":"553e7c4c-4351-4691-b0a2-8553bc96f6a3","mctype":"理塘县雄坝乡水肥一体化项目","mckeys":"2018-08-22","mcontent":null,"vtype":null,"pubtime":1534928648000,"time":null,"utime":null,"bunique":null,"rmenuid":null,"cmenumcode":"JYGCJS","ruserid":null,"areacode":"513334","fnum":null,"areaname":"理塘县","typecode":"ZBGG","state":null,"realpubtime":1534928648000,"cuername":null,"fname":null}],"last":false,"totalElements":2206,"totalPages":221,"first":true,"numberOfElements":10,"sort":null,"size":10,"number":0}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * content : [{"id":"ffc62df5-783b-4001-a8e8-fce0beb9f87d","mctype":"石渠县司法局业务用房项目（随机发包）","mckeys":"2018-08-23","mcontent":null,"vtype":null,"pubtime":1534999425000,"time":null,"utime":null,"bunique":null,"rmenuid":null,"cmenumcode":"JYGCJS","ruserid":null,"areacode":"513332","fnum":null,"areaname":"石渠县","typecode":"ZBGG","state":null,"realpubtime":1534999425000,"cuername":null,"fname":null},{"id":"82ef1864-3f6f-49b7-879a-f9804c78cb5e","mctype":"甘孜县大德乡幼儿园建设项目。","mckeys":"2018-08-23","mcontent":null,"vtype":null,"pubtime":1534999416000,"time":null,"utime":null,"bunique":null,"rmenuid":null,"cmenumcode":"JYGCJS","ruserid":null,"areacode":"513328","fnum":null,"areaname":"甘孜县","typecode":"ZBGG","state":null,"realpubtime":1534999416000,"cuername":null,"fname":null},{"id":"9e3a2341-b963-4f5a-bc3b-60c598381e3d","mctype":"国道350线道孚县八美镇龙灯乡松林口至炉霍县城段大修工程 ","mckeys":"2018-08-23","mcontent":null,"vtype":null,"pubtime":1534999408000,"time":null,"utime":null,"bunique":null,"rmenuid":null,"cmenumcode":"JYGCJS","ruserid":null,"areacode":"513326","fnum":null,"areaname":"道孚县","typecode":"ZBGG","state":null,"realpubtime":1534999408000,"cuername":null,"fname":null},{"id":"31885f71-2c6f-4c6e-82a9-87c5e7e24b6c","mctype":"康定市疾病预防控制中心建设项目","mckeys":"2018-08-23","mcontent":null,"vtype":null,"pubtime":1534996431000,"time":null,"utime":null,"bunique":null,"rmenuid":null,"cmenumcode":"JYGCJS","ruserid":null,"areacode":"513321","fnum":null,"areaname":"康定县","typecode":"ZBGG","state":null,"realpubtime":1534996431000,"cuername":null,"fname":null},{"id":"3fcf7f20-0d8c-4d14-ad94-16c8ab6d4ed3","mctype":"色达县洛若镇智慧景区建设工程 〔第二次〕","mckeys":"2018-08-23","mcontent":null,"vtype":null,"pubtime":1534996425000,"time":null,"utime":null,"bunique":null,"rmenuid":null,"cmenumcode":"JYGCJS","ruserid":null,"areacode":"513333","fnum":null,"areaname":"色达县","typecode":"ZBGG","state":null,"realpubtime":1534996425000,"cuername":null,"fname":null},{"id":"d35eb244-11a8-4439-90b9-a4dbf809f3b2","mctype":"国道318线康定\u2014雅江段 \u201c交通+旅游\u201d 融合发展示范项目一期工程","mckeys":"2018-08-22","mcontent":null,"vtype":null,"pubtime":1534931528000,"time":null,"utime":null,"bunique":null,"rmenuid":null,"cmenumcode":"JYGCJS","ruserid":null,"areacode":"513321","fnum":null,"areaname":"康定县","typecode":"ZBGG","state":null,"realpubtime":1534931528000,"cuername":null,"fname":null},{"id":"f4756a60-674d-4f7a-947f-e4591f92d6a2","mctype":"炉霍县雅德乡交纳村\u201c最美藏家庭院、幸福美丽新村\u201d庭院改造建设项目","mckeys":"2018-08-22","mcontent":null,"vtype":null,"pubtime":1534931248000,"time":null,"utime":null,"bunique":null,"rmenuid":null,"cmenumcode":"JYGCJS","ruserid":null,"areacode":"513327","fnum":null,"areaname":"炉霍县","typecode":"ZBGG","state":null,"realpubtime":1534931248000,"cuername":null,"fname":null},{"id":"05f8e338-b64b-49a1-b8c7-e41298f7cff3","mctype":"巴塘县地巫乡四点坝村通畅工程监理","mckeys":"2018-08-22","mcontent":null,"vtype":null,"pubtime":1534928723000,"time":null,"utime":null,"bunique":null,"rmenuid":null,"cmenumcode":"JYGCJS","ruserid":null,"areacode":"513335","fnum":null,"areaname":"巴塘县","typecode":"ZBGG","state":null,"realpubtime":1534928723000,"cuername":null,"fname":null},{"id":"0bccd1da-186b-47d4-a15f-0c2cbca1c42b","mctype":"炉霍县乡镇干部职工周转房建设项目","mckeys":"2018-08-22","mcontent":null,"vtype":null,"pubtime":1534928686000,"time":null,"utime":null,"bunique":null,"rmenuid":null,"cmenumcode":"JYGCJS","ruserid":null,"areacode":"513327","fnum":null,"areaname":"炉霍县","typecode":"ZBGG","state":null,"realpubtime":1534928686000,"cuername":null,"fname":null},{"id":"553e7c4c-4351-4691-b0a2-8553bc96f6a3","mctype":"理塘县雄坝乡水肥一体化项目","mckeys":"2018-08-22","mcontent":null,"vtype":null,"pubtime":1534928648000,"time":null,"utime":null,"bunique":null,"rmenuid":null,"cmenumcode":"JYGCJS","ruserid":null,"areacode":"513334","fnum":null,"areaname":"理塘县","typecode":"ZBGG","state":null,"realpubtime":1534928648000,"cuername":null,"fname":null}]
         * last : false
         * totalElements : 2206
         * totalPages : 221
         * first : true
         * numberOfElements : 10
         * sort : null
         * size : 10
         * number : 0
         */

        private boolean last;
        private int totalElements;
        private int totalPages;
        private boolean first;
        private int numberOfElements;
        private Object sort;
        private int size;
        private int number;
        private List<ContentBean> content;

        public boolean isLast() {
            return last;
        }

        public void setLast(boolean last) {
            this.last = last;
        }

        public int getTotalElements() {
            return totalElements;
        }

        public void setTotalElements(int totalElements) {
            this.totalElements = totalElements;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public boolean isFirst() {
            return first;
        }

        public void setFirst(boolean first) {
            this.first = first;
        }

        public int getNumberOfElements() {
            return numberOfElements;
        }

        public void setNumberOfElements(int numberOfElements) {
            this.numberOfElements = numberOfElements;
        }

        public Object getSort() {
            return sort;
        }

        public void setSort(Object sort) {
            this.sort = sort;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public List<ContentBean> getContent() {
            return content;
        }

        public void setContent(List<ContentBean> content) {
            this.content = content;
        }

        public static class ContentBean {
            /**
             * id : ffc62df5-783b-4001-a8e8-fce0beb9f87d
             * mctype : 石渠县司法局业务用房项目（随机发包）
             * mckeys : 2018-08-23
             * mcontent : null
             * vtype : null
             * pubtime : 1534999425000
             * time : null
             * utime : null
             * bunique : null
             * rmenuid : null
             * cmenumcode : JYGCJS
             * ruserid : null
             * areacode : 513332
             * fnum : null
             * areaname : 石渠县
             * typecode : ZBGG
             * state : null
             * realpubtime : 1534999425000
             * cuername : null
             * fname : null
             */

            private String id;
            private String mctype;
            private String mckeys;
            private Object mcontent;
            private Object vtype;
            private long pubtime;
            private Object time;
            private Object utime;
            private Object bunique;
            private Object rmenuid;
            private String cmenumcode;
            private Object ruserid;
            private String areacode;
            private Object fnum;
            private String areaname;
            private String typecode;
            private Object state;
            private long realpubtime;
            private Object cuername;
            private Object fname;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getMctype() {
                return mctype;
            }

            public void setMctype(String mctype) {
                this.mctype = mctype;
            }

            public String getMckeys() {
                return mckeys;
            }

            public void setMckeys(String mckeys) {
                this.mckeys = mckeys;
            }

            public Object getMcontent() {
                return mcontent;
            }

            public void setMcontent(Object mcontent) {
                this.mcontent = mcontent;
            }

            public Object getVtype() {
                return vtype;
            }

            public void setVtype(Object vtype) {
                this.vtype = vtype;
            }

            public long getPubtime() {
                return pubtime;
            }

            public void setPubtime(long pubtime) {
                this.pubtime = pubtime;
            }

            public Object getTime() {
                return time;
            }

            public void setTime(Object time) {
                this.time = time;
            }

            public Object getUtime() {
                return utime;
            }

            public void setUtime(Object utime) {
                this.utime = utime;
            }

            public Object getBunique() {
                return bunique;
            }

            public void setBunique(Object bunique) {
                this.bunique = bunique;
            }

            public Object getRmenuid() {
                return rmenuid;
            }

            public void setRmenuid(Object rmenuid) {
                this.rmenuid = rmenuid;
            }

            public String getCmenumcode() {
                return cmenumcode;
            }

            public void setCmenumcode(String cmenumcode) {
                this.cmenumcode = cmenumcode;
            }

            public Object getRuserid() {
                return ruserid;
            }

            public void setRuserid(Object ruserid) {
                this.ruserid = ruserid;
            }

            public String getAreacode() {
                return areacode;
            }

            public void setAreacode(String areacode) {
                this.areacode = areacode;
            }

            public Object getFnum() {
                return fnum;
            }

            public void setFnum(Object fnum) {
                this.fnum = fnum;
            }

            public String getAreaname() {
                return areaname;
            }

            public void setAreaname(String areaname) {
                this.areaname = areaname;
            }

            public String getTypecode() {
                return typecode;
            }

            public void setTypecode(String typecode) {
                this.typecode = typecode;
            }

            public Object getState() {
                return state;
            }

            public void setState(Object state) {
                this.state = state;
            }

            public long getRealpubtime() {
                return realpubtime;
            }

            public void setRealpubtime(long realpubtime) {
                this.realpubtime = realpubtime;
            }

            public Object getCuername() {
                return cuername;
            }

            public void setCuername(Object cuername) {
                this.cuername = cuername;
            }

            public Object getFname() {
                return fname;
            }

            public void setFname(Object fname) {
                this.fname = fname;
            }
        }
    }
}
