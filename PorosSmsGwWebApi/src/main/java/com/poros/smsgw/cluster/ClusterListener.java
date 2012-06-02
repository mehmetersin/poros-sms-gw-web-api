package com.poros.smsgw.cluster;

import java.util.concurrent.BlockingQueue;

import com.hazelcast.core.Hazelcast;
import com.poros.smsgw.ms.MessageItem;

public class ClusterListener {

	

	public static void putMessageQueue(MessageItem message) {
		BlockingQueue<MessageItem> q = Hazelcast.getQueue("tasks");
		q.add(message);
	}
	
	public static MessageItem getMessage(){
		BlockingQueue<MessageItem> q = Hazelcast.getQueue("tasks");
		return q.poll();
	}

//	public static boolean isMaster(){
//		
//		Cluster cluster = Hazelcast.getCluster();
//		
//		Member localMember  = cluster.get getLocalMember();
//		System.out.println ("my inetAddress= " + localMember.getInetAddress());
//		
//		
//		Set<Member> setMembers  = cluster.getMembers();
//		for (Member member : setMembers) {
//			
//			if (localMember.getInetAddress() == )
//			
//			System.out.println ("isLocalMember " + member.localMember());
//			System.out.println ("member.inetaddress " + member.getInetAddress());
//			System.out.println ("member.port " + member.getPort()); 
//		}
//	}
	
	
//	public static void main(String[] args) {
//		
//		Cluster cluster = Hazelcast.getCluster();
//		cluster.addMembershipListener(new MembershipListener(){
//			public void memberAdded(MembershipEvent membersipEvent) {
//				System.out.println("MemberAdded " + membersipEvent);
//			}
//
//			public void memberRemoved(MembershipEvent membersipEvent) {
//				System.out.println("MemberRemoved " + membersipEvent.getMember());
//			}
//		});
//
//		Member localMember  = cluster.getLocalMember();
//		System.out.println ("my inetAddress= " + localMember.getInetSocketAddress(). .getInetAddress());
//
//		Set<Member> setMembers  = cluster.getMembers();
//		for (Member member : setMembers) {
//			System.out.println ("isLocalMember " + member.localMember());
//			System.out.println ("member.inetaddress " + member.getInetAddress());
//			System.out.println ("member.port " + member.getPort()); 
//		}
//	}

}
