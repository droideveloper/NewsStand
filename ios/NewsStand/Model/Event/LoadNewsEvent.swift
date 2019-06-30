//
//  LoadNewsEvent.swift
//  NewsStand
//
//  Created by Fatih Şen on 29.06.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import MVICocoa
import Swinject

class LoadNewsEvent: Event {

	private let source: Source
	
	init(source: Source) {
		self.source = source
  }

  override func toIntent(container: Container?) -> Intent {
		if let newsRepository = container?.resolve(NewsRepository.self) {
			return LoadNewsIntent(source: source.id ?? "", newsRepository: newsRepository)
		}
    return super.toIntent(container: container)
  }
}
