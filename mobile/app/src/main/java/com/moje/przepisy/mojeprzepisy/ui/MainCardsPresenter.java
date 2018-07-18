package com.moje.przepisy.mojeprzepisy.ui;

import com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.OperationsOnCardRepository;

public class MainCardsPresenter implements MainCardsContract.Presenter,
    OperationsOnCardRepository.OnCardsListener {
  private OperationsOnCardRepository operationsOnCardRepository;
  private MainCardsContract.View cardsView;

  public MainCardsPresenter(MainCardsContract.View cardsView, OperationsOnCardRepository operationsOnCardRepository) {
    this.cardsView = cardsView;
    this.operationsOnCardRepository = operationsOnCardRepository;
  }

}
