package net.sanhedao.lawcloudserver.bean;

import java.util.List;

/**
 * 城市Assets中citys.json解析类
 *
 * @author NEGIER
 * @date 2018/2/8
 */
public class Citys {

    /**
     * id : 2
     * regioncode : 110000
     * regionname : 北京市
     * parentid : 1
     * sub : [{"id":"33","regioncode":"110100","regionname":"市辖区","parentid":"2","sub":[{"id":"378","regioncode":"110101","regionname":"东城区","parentid":"33"},{"id":"379","regioncode":"110102","regionname":"西城区","parentid":"33"},{"id":"382","regioncode":"110105","regionname":"朝阳区","parentid":"33"},{"id":"383","regioncode":"110106","regionname":"丰台区","parentid":"33"},{"id":"384","regioncode":"110107","regionname":"石景山区","parentid":"33"},{"id":"385","regioncode":"110108","regionname":"海淀区","parentid":"33"},{"id":"386","regioncode":"110109","regionname":"门头沟区","parentid":"33"},{"id":"387","regioncode":"110111","regionname":"房山区","parentid":"33"},{"id":"388","regioncode":"110112","regionname":"通州区","parentid":"33"},{"id":"389","regioncode":"110113","regionname":"顺义区","parentid":"33"},{"id":"390","regioncode":"110114","regionname":"昌平区","parentid":"33"},{"id":"391","regioncode":"110115","regionname":"大兴区","parentid":"33"},{"id":"392","regioncode":"110116","regionname":"怀柔区","parentid":"33"},{"id":"393","regioncode":"110117","regionname":"平谷区","parentid":"33"}]},{"id":"34","regioncode":"110200","regionname":"县","parentid":"2","sub":[{"id":"394","regioncode":"110228","regionname":"密云县","parentid":"34"},{"id":"395","regioncode":"110229","regionname":"延庆县","parentid":"34"}]}]
     */

    private List<Province> list;

    public List<Province> getList() {
        return list;
    }

    public void setList(List<Province> list) {
        this.list = list;
    }

    public class Province{
        private String id;
        private String regioncode;
        private String regionname;
        private String parentid;
        private List<City> sub;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRegioncode() {
            return regioncode;
        }

        public void setRegioncode(String regioncode) {
            this.regioncode = regioncode;
        }

        public String getRegionname() {
            return regionname;
        }

        public void setRegionname(String regionname) {
            this.regionname = regionname;
        }

        public String getParentid() {
            return parentid;
        }

        public void setParentid(String parentid) {
            this.parentid = parentid;
        }

        public List<City> getSub() {
            return sub;
        }

        public void setSub(List<City> sub) {
            this.sub = sub;
        }

        @Override
        public String toString() {
            return regionname;
        }

        public class City {
            /**
             * id : 33
             * regioncode : 110100
             * regionname : 市辖区
             * parentid : 2
             * sub : [{"id":"378","regioncode":"110101","regionname":"东城区","parentid":"33"},{"id":"379","regioncode":"110102","regionname":"西城区","parentid":"33"},{"id":"382","regioncode":"110105","regionname":"朝阳区","parentid":"33"},{"id":"383","regioncode":"110106","regionname":"丰台区","parentid":"33"},{"id":"384","regioncode":"110107","regionname":"石景山区","parentid":"33"},{"id":"385","regioncode":"110108","regionname":"海淀区","parentid":"33"},{"id":"386","regioncode":"110109","regionname":"门头沟区","parentid":"33"},{"id":"387","regioncode":"110111","regionname":"房山区","parentid":"33"},{"id":"388","regioncode":"110112","regionname":"通州区","parentid":"33"},{"id":"389","regioncode":"110113","regionname":"顺义区","parentid":"33"},{"id":"390","regioncode":"110114","regionname":"昌平区","parentid":"33"},{"id":"391","regioncode":"110115","regionname":"大兴区","parentid":"33"},{"id":"392","regioncode":"110116","regionname":"怀柔区","parentid":"33"},{"id":"393","regioncode":"110117","regionname":"平谷区","parentid":"33"}]
             */

            private String id;
            private String regioncode;
            private String regionname;
            private String parentid;
            private List<District> sub;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getRegioncode() {
                return regioncode;
            }

            public void setRegioncode(String regioncode) {
                this.regioncode = regioncode;
            }

            public String getRegionname() {
                return regionname;
            }

            public void setRegionname(String regionname) {
                this.regionname = regionname;
            }

            public String getParentid() {
                return parentid;
            }

            public void setParentid(String parentid) {
                this.parentid = parentid;
            }

            public List<District> getSub() {
                return sub;
            }

            public void setSub(List<District> sub) {
                this.sub = sub;
            }

            @Override
            public String toString() {
                return regionname;
            }

            public class District {
                /**
                 * id : 378
                 * regioncode : 110101
                 * regionname : 东城区
                 * parentid : 33
                 */

                private String id;
                private String regioncode;
                private String regionname;
                private String parentid;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getRegioncode() {
                    return regioncode;
                }

                public void setRegioncode(String regioncode) {
                    this.regioncode = regioncode;
                }

                public String getRegionname() {
                    return regionname;
                }

                public void setRegionname(String regionname) {
                    this.regionname = regionname;
                }

                public String getParentid() {
                    return parentid;
                }

                public void setParentid(String parentid) {
                    this.parentid = parentid;
                }

                @Override
                public String toString() {
                    return regionname;
                }
            }
        }

    }



}
