package starter.Data;

public class PUFactoryByUser {
        private String PUId;
        private String PUName;
        private String FactoryId;
        private String FactoryName;

        public String getPUId(){
            return PUId;
        }

        public void setPUId(String name){
            this.PUId =name;
        }

        public String getPUName(){
            return PUName;
        }

        public void setPUName(String PUName){
            this.PUName = PUName;
        }

        public String getFactoryId(){
            return FactoryId;
        }

        public void setFactoryId(String factoryId){
            this.FactoryId = factoryId;
        }

        public String getFactoryName(){
            return FactoryName;
        }

        public void setFactoryName(String factoryName){
            this.FactoryName = factoryName;
        }
    }

