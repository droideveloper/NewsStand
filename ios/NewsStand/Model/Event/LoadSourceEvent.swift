//
//  LoadSourceEvent.swift
//  NewsStand
//
//  Created by Fatih Şen on 29.06.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import MVICocoa
import Swinject

class LoadSourceEvent: Event {

  override func toIntent(container: Container?) -> Intent {
		if let sourceRepository = container?.resolve(SourceRepository.self) {
			return LoadSoruceIntent(sourceRepository: sourceRepository)
		}
		return super.toIntent(container: container) // will provide nothing intent if there is no intent relative to this event
  }
}
