package edu.fau.simplechat.client;

import java.util.HashMap;
import java.util.UUID;

import edu.fau.simplechat.request.ClientRequest;

/**
 * Holds a list of ClientRequests
 * @author kyle
 */
public class RequestList {

	/**
	 * Maps UUID to ClientRequest
	 */
	private HashMap<UUID,ClientRequest> requestList;
	
	/**
	 * Initialize RequestLIst
	 */
	public RequestList()
	{
		requestList = new HashMap<>();
	}
	
	/**
	 * Add a request to the list
	 * @param request
	 * @return
	 * @precondition 
	 * @postcondition
	 */
	public boolean add(ClientRequest request)
	{
		if(requestList.containsKey(request.getUserId()))
		{
			return false;
		}
		requestList.put(request.getUserId(),request);
		
		return true;
	}
	
	public boolean remove(ClientRequest request)
	{
		requestList.remove(request.getUserId());
		return true;
	}
	
	public ClientRequest getRequestById(UUID requestId)
	{
		return requestList.get(requestId);
	}

	public boolean remove(UUID requestId) {
		requestList.remove(requestId);
		return true;
		
	}
}
