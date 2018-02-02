package lab7;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.*;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

class NoSuchRoomExcåption extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoSuchRoomExcåption(String roomName){
		super(roomName);
	}
}

class NoSuchUserException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoSuchUserException(String userName){
		super(userName);
	}
}

class User implements Comparable<User>{
	
	public String userName;
	
	public User(String u){
		userName=u;
	}
	
	public String toString() {
		return userName+"\n";
	}

	@Override
	public int compareTo(User arg0) {
		return userName.compareTo(arg0.userName);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}	
}

class ChatRoom {
	public String name;
	public TreeSet<User> users;
	
	public ChatRoom(String name){
		this.name=name;
		users = new TreeSet<>();
	}
	
	public void addUser(String username){
		users.add(new User(username));
	}
	
	public void removeUser(String username) {
		User u = new User(username);
		users.remove(u);
	}
	
	public String toString() {
		if (users.isEmpty())
			return name +"\n"+"EMPTY";
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(name).append("\n");
		users.stream().forEach(x -> sb.append(x.toString()));
		return sb.toString();
	}
	
	public boolean hasUser(String username){
		User u = new User(username);
		return users.contains(u);			
	}
	
	public int numUsers() {
		return users.size();
	}

	
	
}

class ChatSystem {
	TreeMap<String,ChatRoom> rooms;
	HashSet<User> users;
	
	public ChatSystem() {
		rooms = new TreeMap<>();
		users = new HashSet<>();
	}
	
	public void addRoom(String name) {
		rooms.put(name, new ChatRoom(name));
	}
	
	public void removeRoom (String name){
		rooms.remove(name);
	}
	
	public ChatRoom getRoom(String name){
		return rooms.get(name);
	}
	
	public void register (String userName) {
		users.add(new User(userName));
		if (rooms.isEmpty()) {
			return;
		}
		int max = rooms.values().stream().mapToInt(x -> x.numUsers()).min().getAsInt();
		System.out.println(max);
		List<ChatRoom> rooms1 = new ArrayList<>();
		rooms1 = rooms.values().stream().filter(x -> x.users.size()==max).collect(Collectors.toList());
		rooms1.sort((r1,r2) -> r1.name.compareTo(r2.name));
		ChatRoom cr = rooms1.get(0);
		cr.users.add(new User(userName));
		rooms.remove(cr.name);
		rooms.put(cr.name, cr);
		
		
	}
	
	public void registerAndJoin(String userName, String roomName) throws NoSuchRoomExcåption{
		users.add(new User(userName));
		joinRoom(userName,roomName);
	}
	
	public void joinRoom (String userName, String roomName) throws NoSuchRoomExcåption{
		ChatRoom cr = rooms.get(roomName);
		if (cr==null)
			throw new NoSuchRoomExcåption(roomName);
		
		cr.users.add(new User(userName));
		rooms.remove(cr.name);
		rooms.put(cr.name, cr);
		
		users.add(new User(userName));		
	}
	
	public void leaveRoom (String userName, String roomName) throws NoSuchRoomExcåption, NoSuchUserException{
		if (!rooms.containsKey(roomName))
			throw new NoSuchRoomExcåption(roomName);
		
		if (!rooms.get(roomName).hasUser(userName))
			throw new NoSuchUserException(userName);
		
		rooms.computeIfPresent(roomName, (rName,room) -> {
            room.removeUser(userName);
            return room;
        });
	}
	
	public void followFriend (String userName, String friendUserName) throws NoSuchUserException{
		if (!users.contains(new User(friendUserName)))
			throw new NoSuchUserException(friendUserName);
		List<ChatRoom> rooms1 = new ArrayList<>();
		rooms1 = rooms.values().stream().filter(x -> x.hasUser(friendUserName)).collect(Collectors.toList());
		rooms1.stream().forEach(x -> rooms.remove(x.name));
		rooms1.stream().forEach(x -> x.addUser(userName));
		rooms1.stream().forEach(x -> rooms.put(x.name, x));
		
			
	}
}

public class ChatSystemTest {
	
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchRoomExcåption {
		Scanner jin = new Scanner(System.in);																				    
		int k = jin.nextInt();
		if ( k == 0 ) {
			ChatRoom cr = new ChatRoom(jin.next());
			int n = jin.nextInt();
			for ( int i = 0 ; i < n ; ++i ) {
				k = jin.nextInt();
				if ( k == 0 ) cr.addUser(jin.next());
				if ( k == 1 ) cr.removeUser(jin.next()); 
				if ( k == 2 ) System.out.println(cr.hasUser(jin.next()));  
			}
			System.out.println("");
			System.out.println(cr.toString());
			n = jin.nextInt();
			if ( n == 0 ) return;
			ChatRoom cr2 = new ChatRoom(jin.next());
			for ( int i = 0 ; i < n ; ++i ) {
				k = jin.nextInt();
				if ( k == 0 ) cr2.addUser(jin.next());
				if ( k == 1 ) cr2.removeUser(jin.next()); 
				if ( k == 2 ) cr2.hasUser(jin.next());  
			}
            System.out.println(cr2.toString());
		}	
       if ( k == 1 ) {
			ChatSystem cs = new ChatSystem();
			Method mts[] = cs.getClass().getMethods();
			while ( true ) {
				String cmd = jin.next();
				if ( cmd.equals("stop") ) break;
				if ( cmd.equals("print") ) {
					System.out.println(cs.getRoom(jin.next())+"\n");continue;
				}
				for ( Method m : mts ) {
					if ( m.getName().equals(cmd) ) {
						String params[] = new String[m.getParameterTypes().length];
						for ( int i = 0 ; i < params.length ; ++i ) params[i] = jin.next();
						m.invoke(cs,params);
					}
				}				
			}
		}
	}

}
