//
//  TabletRootviewController.swift
//  NewsStand
//
//  Created by Fatih Şen on 30.06.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import Swinject
import SwinjectStoryboard

class TabletRootViewController: UIViewController {
	
	override func viewDidLoad() {
		super.viewDidLoad()
	
		guard let container = container else {
			fatalError("could not resolve \(Container.self)")
		}
		
		let f = CGFloat(0.35)
		let bounds = UIScreen.main.bounds
		let sourceBounds = CGRect(x: 0, y: 0, width: bounds.width * f, height: bounds.height)
		let newsBounds = CGRect(x: bounds.width * f, y: 0, width: bounds.width * (1 - f), height: bounds.height)
		
		let storyBoard = SwinjectStoryboard.create(name: "Main", bundle: .main, container: container)
		let sourceController = storyBoard.instantiateViewController(withIdentifier: String(describing: SourceController.self))
		let newsController = storyBoard.instantiateViewController(withIdentifier: String(describing: NewsController.self))
		
		let sourceRootController = HandsetRootViewController(rootViewController: sourceController)
		let newsRootController = HandsetRootViewController(rootViewController: newsController)
		
		attachToParent(child: sourceRootController, bounds: sourceBounds)
		attachToParent(child: newsRootController, bounds: newsBounds)
	}
	
	private func attachToParent(child: UIViewController, bounds: CGRect) {
		addChild(child)
		child.view.frame = bounds
		view.addSubview(child.view)
		child.didMove(toParent: self)
	}
}
