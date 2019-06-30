//
//  Device+Extension.swift
//  NewsStand
//
//  Created by Fatih Şen on 29.06.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import UIKit

extension UIDevice {
	
	public static var isTabletDevice: Bool {
		return UIDevice.current.userInterfaceIdiom == .pad
	}
}
