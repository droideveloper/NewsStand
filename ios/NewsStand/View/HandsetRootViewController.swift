//
//  HandsetRootViewController.swift
//  NewsStand
//
//  Created by Fatih Şen on 30.06.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import UIKit

class HandsetRootViewController: UINavigationController {
	
	override func viewDidLoad() {
		super.viewDidLoad()
		// will do initla set up in here
		navigationBar.barStyle = .black
		navigationBar.barTintColor = .blue
		navigationBar.tintColor = .white
		navigationBar.titleTextAttributes = [NSAttributedString.Key.foregroundColor: UIColor.white]
	}
}
