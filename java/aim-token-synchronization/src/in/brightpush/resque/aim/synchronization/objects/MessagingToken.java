package in.brightpush.resque.aim.synchronization.objects;

import java.util.List;

public class MessagingToken {
	private Key key;
	
    private String applicationId;
	
	private String bundleId;
	
	private List<String> deviceIds;

	private String appVersion;
	
	private String sdkVersion;
	
	private String platform;
	
	private boolean testMode;
	
	private long last_registration_time;
	
	private List<String> tags;
	
	private List<String> deviceSpecs;

	private boolean active;

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getBundleId() {
		return bundleId;
	}

	public void setBundleId(String bundleId) {
		this.bundleId = bundleId;
	}

	public List<String> getDeviceIds() {
		return deviceIds;
	}

	public void setDeviceIds(List<String> deviceId) {
		this.deviceIds = deviceId;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getSdkVersion() {
		return sdkVersion;
	}

	public void setSdkVersion(String sdkVersion) {
		this.sdkVersion = sdkVersion;
	}

	public long getLast_registration_time() {
		return last_registration_time;
	}

	public void setLast_registration_time(long last_registration_time) {
		this.last_registration_time = last_registration_time;
	}
	
	public List<String> getTags() {
		return tags;
	}
	
	public String getTagsAsString() {
		if(this.getTags() == null || this.getTags().isEmpty())
			return "";
		
	    StringBuilder sb = new StringBuilder();
	    String loopDelim = "";
	    for(String s : getTags()) {
	        sb.append(loopDelim);
	        sb.append(s);            
	        loopDelim = ",";
	    }

	    return sb.toString();
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	
	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public List<String> getDeviceSpecs() {
		return deviceSpecs;
	}

	public void setDeviceSpecs(List<String> deviceSpecs) {
		this.deviceSpecs = deviceSpecs;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public boolean isTestMode() {
		return testMode;
	}

	public void setTestMode(boolean testMode) {
		this.testMode = testMode;
	}
}
