//
//  HandsetNavigation.swift
//  NewsStand
//
//  Created by Fatih Şen on 29.06.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import UIKit
import Swinject
import SwinjectStoryboard

class HandsetNavigation: Navigation<Source> {
	
	override func navigate(controller: UIViewController, payload: Source?) {
		guard let container = controller.container else { return }
		guard let navigationController = controller.navigationController else { return }
		
		let storyBoard = SwinjectStoryboard.create(name: "Main", bundle: .main, container: container)
		let newsController = storyBoard.instantiateViewController(withIdentifier: String(describing: NewsController.self))
		if let newsController = newsController as? NewsController {
			newsController.source = payload ?? Source.empty
			navigationController.pushViewController(newsController, animated: true)
		}
	}
}
