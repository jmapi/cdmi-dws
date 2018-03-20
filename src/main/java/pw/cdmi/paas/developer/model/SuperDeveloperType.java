package pw.cdmi.paas.developer.model;



public enum SuperDeveloperType {
	Developer_personal,
	Developer_enterprise;
	
	public static SuperDeveloperType fromName(String name) {
        for (SuperDeveloperType item : SuperDeveloperType.values()) {
            if (item.toString().equals(name)) {
                return item;
            }
        }
        return null;
    }
}
