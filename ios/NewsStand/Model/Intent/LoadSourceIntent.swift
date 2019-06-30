//
//  LoadSourceIntent.swift
//  NewsStand
//
//  Created by Fatih Şen on 29.06.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import RxSwift
import MVICocoa

class LoadSoruceIntent: ObservableIntent<SourceModel> {
	
	private let sourceRepository: SourceRepository
	
	init(sourceRepository: SourceRepository) {
		self.sourceRepository = sourceRepository
	}
	
	override func invoke() -> Observable<(SourceModel) -> SourceModel> {
		return sourceRepository.sources()
			.concatMap(success(_ :))
			.catchError(failure(_:))
			.startWith(initial())
			.delay(RxTimeInterval.milliseconds(500), scheduler: MainScheduler.asyncInstance)
			.subscribeOn(MainScheduler.asyncInstance)
	}
	
	private func initial() -> Reducer<SourceModel> {
		return { o in o.copy(state: .operationOf(refresh), data: []) }
	}
	
	private func success(_ resource: Resource<[Source]>) -> Observable<Reducer<SourceModel>> {
		switch resource {
			case .success(let data, _): return Observable.of(
				{ o in o.copy(state: .operationOf(refresh), data: data ?? [])},
				{ o in o.copy(state: .idle, data: []) })
			case .failure(_, let message): return Observable.of(
				{ o in o.copy(state: .failure(MVIError.of(description: message ?? ""))) },
				{ o in o.copy(state: .idle) })
		}
	}
	
	private func failure(_ error: Error) -> Observable<Reducer<SourceModel>> {
		return Observable.of(
			{ o in o.copy(state: .failure(error)) },
			{ o in o.copy(state: .idle) })
	}
}
