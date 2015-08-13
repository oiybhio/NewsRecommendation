package Database;

public class UserDatabase {
    public UserDatabase(){
    	
    }
    public void setUser(User user){
    	return ;
    }
    public User getUser(long uid){
    	return new User();
    }
    public void removeUser(long uid){
    	
    }
    
    public boolean isExistUser(long uid){
    	return true;
    }
}
