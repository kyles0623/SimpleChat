package edu.fau.simplechat.request;

import java.util.UUID;

import edu.fau.simplechat.model.GroupModel;
/**
 * Represents a request to Delete a Group
 * @author kyle
 *
 */
public class DeleteGroupRequest extends ClientRequest{

	/**
	 *  Serializable ID
	 */
	private static final long serialVersionUID = 6310493054832780270L;

	/**
	 * Group being deleted
	 */
	private final GroupModel group;

	/**
	 * Initialize DeleteGroupRequest
	 * @param userId ID of user deleting group
	 * @param group Group being deleted
	 */
	public DeleteGroupRequest(final UUID userId, final GroupModel group) {
		super(userId);
		this.group = group;
		// TODO Auto-generated constructor stub
	}

	/**
	 * Retrieve Group requested to be deleted
	 * @return Group requested to be deleted
	 * @precondition none
	 * @postcondition Group is returned
	 */
	public GroupModel getGroup()
	{
		return group;
	}

}
