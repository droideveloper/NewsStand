//
//  NewsControllerViewModel.swift
//  NewsStand
//
//  Created by Fatih Şen on 30.06.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import MVICocoa
import RxSwift

class NewsControllerViewModel: BaseViewModel<NewsModel> {
  
  private weak var view: NewsController?

  init(view: NewsController) {
    self.view = view
  }

  override func attach() {
    super.attach()
    // if no view ignore
    guard let view = view else { return }
    // convert view events into relative intent and pass them pipeline
    disposeBag += view.viewEvents()
      .toIntent(view.container)
      .subscribe(onNext: accept(_ :))
  }
}