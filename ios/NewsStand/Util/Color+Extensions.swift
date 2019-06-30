//
//  Color+Extensions.swift
//  NewsStand
//
//  Created by Fatih Şen on 29.06.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import MVICocoa
import UIKit

extension UIColor {
	
	public static var colorToolbar: UIColor  {
		return UIColor.convert(0x292AFA)
	}
	
	public static var colorToolbarTitle: UIColor {
		return .white
	}
	
	public static var colorTitleText: UIColor {
		return UIColor.convert(0x66A2F2)
	}
	
	public static var colorSpotText: UIColor {
		return UIColor.convert(0x313131)
	}
	
	public static var opaque50ColorSpotText: UIColor {
		return colorSpotText.withAlphaComponent(0.5)
	}
	
	public static var backgroundColor: UIColor {
		return UIColor.convert(0xCDCDCD)
	}
}
