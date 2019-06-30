//
//  SourceController.swift
//  NewsStand
//
//  Created by Fatih Şen on 29.06.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import MVICocoa

class SourceController: BaseTableViewController<SourceModel, SourceControllerViewModel> {

  private var dataSet = ObservableList<Source>()
  private lazy var dataSource = {
    return SourceDataSource(dataSet: dataSet)
  }()

	var naviagtion: Navigation<Source>? = nil
	
  override func setUp() {
		title = NSLocalizedString("sources-title", comment: "")
		
		let refreshControl = UIRefreshControl()
		refreshControl.tintColor = .red
		
		tableView.refreshControl = refreshControl
		// register cells
		tableView.register(SourceSimpleCell.self)
		// register data source
		tableView.dataSource = dataSource
  }

  override func attach() {
    super.attach()
    dataSet.register(tableView)

		disposeBag += BusManager.register { [weak weakSelf = self] evt in
			if let evt = evt as? SelectSourceEvent {
				weakSelf?.naviagtion?.navigate(controller: self, payload: evt.source)
			}
		}
		
		checkIfInitialLoadNeeded()
		
		// register pull to refresh
		guard let refreshControl = refreshControl else { return }
		
		disposeBag += refreshControl.rx.controlEvent(.valueChanged)
			.map { _ in LoadSourceEvent() }
			.do(onNext: { [weak weakSelf = self] _ in
				weakSelf?.dataSet.clear()
			})
			.subscribe(onNext: accept(_ : ))
  }

  override func render(model: SourceModel) {
    switch model.state {
      case .idle: break
			case .failure(let error):
				log(error)
				break
			case .operation(let type, _):
				if type == refresh {
					render(model.data)
				}
				break
    }
  }

  override func viewWillDisappear(_ animated: Bool) {
    dataSet.unregister(tableView)
    super.viewWillDisappear(animated)
  }
	
	private func checkIfInitialLoadNeeded() {
		if dataSet.isEmpty {
			accept(LoadSourceEvent())
		}
	}
	
	private func render(_ data: [Source]) {
		if !data.isEmpty {
			if UIDevice.isTabletDevice && dataSet.isEmpty { // only in tablet devices ve have initial selection
				let first = data.first ?? Source.empty
				BusManager.send(event: SelectSourceEvent(source: first))
			}
			dataSet.append(data)
		}
	}
}
