//
//  UIRefreshControl+Extension.swift
//  NewsStand
//
//  Created by Fatih Şen on 29.06.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import UIKit

extension UIRefreshControl {
	
	public func bindProgress(_ showProgress: Bool) {
		let shouldShowProgress = showProgress && !isRefreshing
		if shouldShowProgress {
			beginRefreshing()
		}
		let shoudStopProgress = !showProgress && isRefreshing
		if shoudStopProgress {
			endRefreshing()
		}
	}
}
