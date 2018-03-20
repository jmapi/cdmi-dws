package pw.cdmi.paas.developer.model;

public enum SuperAppType {
	App_wx;
	
	public static SuperAppType fromName(String name) {
        for (SuperAppType item : SuperAppType.values()) {
            if (item.toString().equals(name)) {
                return item;
            }
        }
        return null;
    }
}
