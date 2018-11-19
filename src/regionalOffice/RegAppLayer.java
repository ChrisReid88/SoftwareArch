package regionalOffice;
;

public class RegAppLayer implements RegAppLayerInterface {
	
	private RegDataLayer dataLayer;

	public RegAppLayer (RegDataLayer dataLayer) {
		this.dataLayer = dataLayer;
	
	}
	
	
	public String addCall(String name) {
		String fname = name;
		boolean success =  dataLayer.addCall(fname);
		if (success) {
			return "ADDED";
		}else {
			return "FAILED";
		}
	}
}
