//
//  ControllerDependency.swift
//  NewsStand
//
//  Created by Fatih Şen on 29.06.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import Swinject
import MVICocoa

class ControllerDependency: Dependency {
	
	private let container: Container
	
	init(container: Container) {
		self.container = container
	}
	
	func register() {

		self.container.storyboardInitCompleted(SourceController.self) { resolver, controller in
			guard let navigation = resolver.resolve(Navigation<Source>.self) else {
				fatalError("can not resolve dependenct \(Navigation<Source>.self)")
			}
			controller.naviagtion = navigation
			controller.viewModel = SourceControllerViewModel(view: controller)
		}
		
		self.container.storyboardInitCompleted(NewsController.self) { _, controller in
			controller.viewModel = NewsControllerViewModel(view: controller)
		}
	}
}
