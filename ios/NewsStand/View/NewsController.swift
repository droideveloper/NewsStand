//
//  NewsController.swift
//  NewsStand
//
//  Created by Fatih Şen on 30.06.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import MVICocoa
import RxSwift

class NewsController: BaseCollectionViewController<NewsModel, NewsControllerViewModel>, Loggable {

  private var dataSet = ObservableList<News>()
	private lazy var dataSource: NewsDataSource = {
		guard let newsRepository = container?.resolve(NewsRepository.self) else {
			fatalError("can not resolve dependency \(NewsRepository.self)")
		}
    return NewsDataSource(dataSet: dataSet, newsRepository: newsRepository)
  }()

	private var refreshControl: UIRefreshControl!
	
	var source = Source.empty {
		didSet {
			title = source.name
		}
	}
	
  override func setUp() {
		let layout = UICollectionViewFlowLayout()
		layout.scrollDirection = .vertical
		if UIDevice.isTabletDevice {
			layout.minimumLineSpacing = 8
			layout.minimumInteritemSpacing = 0
			let width = collectionView.bounds.width / 3
			layout.itemSize = CGSize(width: width - 20, height: width)
		} else {
			layout.minimumLineSpacing = 8
			layout.minimumInteritemSpacing = 8
			let width = collectionView.bounds.width
			layout.itemSize = CGSize(width: width, height: width)
		}
		collectionView.collectionViewLayout = layout
		
		refreshControl = UIRefreshControl()
		refreshControl.tintColor = .red
		
		collectionView.refreshControl = refreshControl
		// register cell
		collectionView.register(NewsSimpleCell.self)
		// register data source
		collectionView.dataSource = dataSource
  }

  override func attach() {
    super.attach()
    dataSet.register(collectionView)

		disposeBag += BusManager.register { [weak weakSelf = self] evt in
			// only in tablet device
			if let evt = evt as? SelectSourceEvent { // tablet device goes on here
				weakSelf?.dataSet.clear()
				weakSelf?.source = evt.source
				weakSelf?.checkIfInitialLoadNeeded()
			} else if let evt = evt as? SelectNewsEvent { // goes browser for detail
				if let url = URL(string: evt.news.url ?? "") {
					UIApplication.shared.open(url, options: [:], completionHandler: nil)
				}
			} else if let evt = evt as? BookmarkNewsEvent {
				weakSelf?.accept(evt)
			}
		}
		
		
		// register pull to refresh
		disposeBag += refreshControl.rx.controlEvent(.valueChanged)
			.map { [weak weakSelf = self] _ in LoadNewsEvent(source: weakSelf?.source ?? Source.empty) }
			.do(onNext: { [weak weakSelf = self] _ in weakSelf?.dataSet.clear() })
			.subscribe(onNext: accept(_ :))
		
		checkIfInitialLoadNeeded()
		
		guard let viewModel = viewModel else {
			fatalError("can not resolve dependency \(NewsControllerViewModel.self)")
		}
		
		disposeBag += viewModel.state()
			.map { (state) -> Bool in
				switch state {
					case .operation(let type, _): return type == refresh
					default: return false
				}
			}
			.subscribe(refreshControl.rx.isRefreshing)
		
	}

  override func render(model: NewsModel) {
    switch model.state {
      case .idle: break;
      case .failure(let error):
				log(error)
				break
      case .operation(let type, _):
				if type == refresh {
					render(model.data)
				} else if type == saveBookmark || type == removeBookmark {
					render(model.changeState)
				}
				break
    }
  }

  override func viewWillDisappear(_ animated: Bool) {
    dataSet.unregister(collectionView)
    super.viewWillDisappear(animated)
  }
	
	private func render(_ data: [News]) {
		if !data.isEmpty {
			dataSet.append(data)
		}
	}
	
	private func render(_ data: News) {
		if data != News.empty {
			let index = dataSet.indexOf(data)
			if index != -1 {
				var newNews = data
				newNews.hasBookmark = !newNews.hasBookmark
				dataSet.put(at: index, value: newNews)
			}
		}
	}
	
	private func checkIfInitialLoadNeeded() {
		if dataSet.isEmpty  && source != Source.empty {
			accept(LoadNewsEvent(source: source))
		}
	}
}
