package cn.zb.commoms.user.model;

import cn.zb.base.model.BaseModel;
import cn.zb.commoms.user.entity.User;


/**
 * 
 * @author chen
 *
 */
public class UserModel extends User implements BaseModel<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5540574036859047684L;
	
	
	private String corpName;
	
	private String typeName;


	public String getCorpName() {
		return corpName;
	}


	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}


    public String getTypeName() {
        return typeName;
    }


    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
	
	

}
