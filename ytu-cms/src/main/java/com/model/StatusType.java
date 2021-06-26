package com.model;

public enum StatusType {
	DRAFT ,		// file is editable and not published to advisor
	REQUESTED,	// file is shared with the advisor, but not accepted yet
	AWAITING,	// file is accepted by the advisor and waiting the acceptance of all departments
	ACCEPTED,	// file is accepted by admins
	REJECTED,	// file is rejeceted by admins
	COMPLETED	// file is accepted and it has completed
}
