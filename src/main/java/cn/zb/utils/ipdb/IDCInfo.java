package cn.zb.utils.ipdb;

public class IDCInfo {

    private String[] data;

    public IDCInfo(String[] data) {
        this.data = data;
    }

    public String getCountryName() {
        return this.data[0];
    }

    public String getRegionName() {
        return this.data[1];
    }

    public String getCityName() {
        return this.data[2];
    }

    public String getOwnerDomain() {
        return this.data[3];
    }

    public String getIspDomain() {
        return this.data[4];
    }

    public String getIDC() {
        return this.data[5];
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();

        sb.append("country_name:");
        sb.append(this.getCountryName());
        sb.append("\n");
        sb.append("region_name:");
        sb.append(this.getRegionName());
        sb.append("\n");
        sb.append("city_name:");
        sb.append(this.getCityName());
        sb.append("\n");
        sb.append("owner_domain:");
        sb.append(this.getOwnerDomain());
        sb.append("\n");
        sb.append("isp_domain:");
        sb.append(this.getIspDomain());
        sb.append("\n");
        sb.append("idc:");
        sb.append(this.getIDC());

        return sb.toString();
    }
}
