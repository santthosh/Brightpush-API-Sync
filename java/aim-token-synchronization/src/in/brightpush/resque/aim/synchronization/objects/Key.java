package in.brightpush.resque.aim.synchronization.objects;

public class Key {
	private String id;
	
	private String name;

	private String kind;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String Id) {
		this.id = Id;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}
}
