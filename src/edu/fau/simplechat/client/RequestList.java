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
	private final HashMap<UUID,ClientRequest> requestList;

	/**
	 * Initialize RequestList
	 * @precondition: none
	 * @postcondition: none
	 */
	public RequestList()
	{
		requestList = new HashMap<>();
	}

	/**
	 * Add a request to the list
	 * @param request
	 * @return true if successful, false isn't already in list
	 * @precondition request isn't already in list
	 * @postcondition request will be in list
	 */
	public boolean add(final ClientRequest request)
	{
		if(requestList.containsKey(request.getUserId()))
		{
			return false;
		}
		requestList.put(request.getUserId(),request);

		return true;
	}

	/**
	 * Remove ClientRequest from list
	 * @param request Request to delete
	 * @precondition request is in the list
	 * @postcondition request will not be in the list
	 */
	public void remove(final ClientRequest request)
	{
		requestList.remove(request.getUserId());
	}

	/**
	 * Remove ClientRequest from list given its ID
	 * @param requestId ID of the request to delete
	 * @precondition requestID is in list
	 * @postcondition ClientRequest will be removed from the list
	 */
	public void remove(final UUID requestId) {
		requestList.remove(requestId);

	}

	/**
	 * Returns a ClientRequest given its id
	 * @param requestId Request ID of the request
	 * @return Request associated with id, null if its not in the list
	 * @precondition request is in list
	 * @postcondition request is returned
	 */
	public ClientRequest getRequestById(final UUID requestId)
	{
		return requestList.get(requestId);
	}


}
