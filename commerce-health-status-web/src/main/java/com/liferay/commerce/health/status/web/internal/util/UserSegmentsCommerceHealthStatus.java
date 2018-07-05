/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.health.status.web.internal.util;

import com.liferay.commerce.health.status.CommerceHealthStatus;
import com.liferay.commerce.health.status.web.internal.constants.CommerceHealthStatusConstants;
import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.service.CommerceCountryLocalService;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry;
import com.liferay.commerce.user.segment.service.CommerceUserSegmentEntryLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"commerce.health.status.display.order:Integer=70",
		"commerce.health.status.key=" + CommerceHealthStatusConstants.USER_SEGMENTS_COMMERCE_HEALTH_STATUS_KEY
	},
	service = CommerceHealthStatus.class
)
public class UserSegmentsCommerceHealthStatus implements CommerceHealthStatus {

	@Override
	public void fixIssue(HttpServletRequest httpServletRequest)
		throws PortalException {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			httpServletRequest);

		_commerceUserSegmentEntryLocalService.
			importSystemCommerceUserSegmentEntries(serviceContext);
	}

	@Override
	public String getDescription(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(
			resourceBundle,
			CommerceHealthStatusConstants.
				USER_SEGMENTS_COMMERCE_HEALTH_STATUS_DESCRIPTION);
	}

	@Override
	public String getKey() {
		return CommerceHealthStatusConstants.
			USER_SEGMENTS_COMMERCE_HEALTH_STATUS_KEY;
	}

	@Override
	public String getName(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(
			resourceBundle,
			CommerceHealthStatusConstants.
				USER_SEGMENTS_COMMERCE_HEALTH_STATUS_KEY);
	}

	@Override
	public boolean isFixed(long groupId) throws PortalException {
		List<CommerceUserSegmentEntry> commerceUserSegmentEntries =
			_commerceUserSegmentEntryLocalService.getCommerceUserSegmentEntries(
				groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		return !commerceUserSegmentEntries.isEmpty();
	}

	@Reference
	private CommerceUserSegmentEntryLocalService
		_commerceUserSegmentEntryLocalService;

}