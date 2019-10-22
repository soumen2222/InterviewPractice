
public class BuilderPattern {
	private String var1;
	private String Var2;
	
	private BuilderPattern(Builder b) {
		this.var1 =b.var1;
		this.Var2=b.Var2;
		
	}
	
	public static class Builder{
		
		private String var1;
		private String Var2;
		
		public Builder setVar1(String var1) {
			this.var1 = var1;
			return this;
		}
		
		public Builder setVar2(String var2) {
			Var2 = var2;
			return this;
		}
		
		public BuilderPattern build()
		{
			return new BuilderPattern(this);
		}		
		
	}

}
