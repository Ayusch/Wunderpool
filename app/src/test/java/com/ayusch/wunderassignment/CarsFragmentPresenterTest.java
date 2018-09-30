package com.ayusch.wunderassignment;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ayusch.wunderassignment.activities.MainActivity;
import com.ayusch.wunderassignment.contracts.CarsFragmentContract;
import com.ayusch.wunderassignment.fragments.cars.CarsFragment;
import com.ayusch.wunderassignment.fragments.cars.CarsFragmentPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

/**
 * Created by hp on 30-Sep-18.
 */

public class CarsFragmentPresenterTest {


    @Mock
    private CarsFragmentContract.View mCarsFragmentView;

    @Mock
    private CarsFragmentContract.Interactor mInteractor;


    private CarsFragmentContract.Presenter presenter;

    @Before
    public void setupCarsFragmentPresenter(){
        MockitoAnnotations.initMocks(this);
        presenter = new CarsFragmentPresenter(mCarsFragmentView);
    }

    @Test
    public void showDialogPCalls_showDialogV(){
        presenter.showDialogP();
        verify(mCarsFragmentView).showDialogV();
    }

    // Would write more test cases but I was out of time. Sorry about that !!


}
