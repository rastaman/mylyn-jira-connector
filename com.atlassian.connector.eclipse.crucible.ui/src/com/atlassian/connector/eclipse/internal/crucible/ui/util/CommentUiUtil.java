/*******************************************************************************
 * Copyright (c) 2009 Atlassian and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Atlassian - initial API and implementation
 ******************************************************************************/

package com.atlassian.connector.eclipse.internal.crucible.ui.util;

import com.atlassian.connector.eclipse.internal.crucible.core.CrucibleConstants;
import com.atlassian.theplugin.commons.crucible.api.model.Comment;
import com.atlassian.theplugin.commons.crucible.api.model.CustomField;
import java.text.DateFormat;
import java.util.Map;

public final class CommentUiUtil {

	private CommentUiUtil() {

	}

	public static String getCommentInfoHeaderText(Comment comment) {
		StringBuilder headerText = new StringBuilder();
		headerText.append(comment.getAuthor().getDisplayName());
		headerText.append("\n");
		headerText.append(DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM)
				.format(comment.getCreateDate()));

		if (comment.getReadState() != null) {
			if (comment.getReadState().equals(Comment.ReadState.READ)) {
				headerText.append(", Read");
			} else if (comment.getReadState().equals(Comment.ReadState.UNREAD)
					|| comment.getReadState().equals(Comment.ReadState.LEAVE_UNREAD)) {
				headerText.append(", Unread");
			}
		}

		if (comment.isDraft()) {
			headerText.append(", ");
			headerText.append("Draft");
		}

		if (comment.isDefectRaised()) {
			headerText.append(", ");
			headerText.append("Defect");

			Map<String, CustomField> fields = comment.getCustomFields();
			if (fields != null) {
				boolean shouldCloseBracket = false;
				if (fields.containsKey(CrucibleConstants.RANK_CUSTOM_FIELD_KEY)) {
					headerText.append(" (");
					shouldCloseBracket = true;
					headerText.append(fields.get(CrucibleConstants.RANK_CUSTOM_FIELD_KEY).getValue());
				}

				if (fields.containsKey(CrucibleConstants.CLASSIFICATION_CUSTOM_FIELD_KEY)) {
					if (shouldCloseBracket) {
						headerText.append(",");
					}
					headerText.append(" ");
					headerText.append(fields.get(CrucibleConstants.CLASSIFICATION_CUSTOM_FIELD_KEY)
							.getValue());
				}
				if (shouldCloseBracket) {
					headerText.append(")");
				}

			}
		}
		return headerText.toString();
	}
}
