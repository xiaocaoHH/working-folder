#!/usr/bin/env python
# coding: utf-8
# 
# The file contains the codes for the exercise
#

# Importing Required Libraries
import pandas as pd
from pandas.api.types import is_numeric_dtype
import numpy as np
import seaborn as sns
import matplotlib.pyplot as plt
import category_encoders as encoder
import warnings

from sklearn.linear_model import LogisticRegression
from sklearn.ensemble import RandomForestClassifier
from sklearn.metrics import classification_report, confusion_matrix
from sklearn import metrics
from sklearn.model_selection import train_test_split
from sklearn import decomposition

from tools import parameter_tuning
from tools import stats_lib

# encode category variables to numeric variables.
def run_encode(df, target):    
    ecr = encoder.OneHotEncoder(cols=target, return_df=True, handle_unknown='return_nan', use_cat_names=True)
    data_encoded = ecr.fit_transform(df[target])
    return data_encoded
    
def encode(df):
    # category encoder
    df['x3'].replace('Mon','Monday',inplace=True)
    df['x3'].replace('Tue','Tuesday',inplace=True)
    df['x3'].replace('Wed','Wednesday',inplace=True)
    df['x3'].replace('Thur','Thursday',inplace=True)
    df['x3'].replace('Fri','Friday',inplace=True)
    df['x3'].replace('Sat','Saturday',inplace=True)
    df['x3'].replace('Sun','Sunday',inplace=True)

    df = pd.concat([df, run_encode(df, 'x3')], axis=1, join='inner')
    del df['x3']    
    
    df = pd.concat([df, run_encode(df, 'x24')], axis=1, join='inner')
    del df['x24']
    
    df = pd.concat([df, run_encode(df, 'x31')], axis=1, join='inner')
    del df['x31']
    
    df = pd.concat([df, run_encode(df, 'x33')], axis=1, join='inner')
    del df['x33']
    
    df = pd.concat([df, run_encode(df, 'x60')], axis=1, join='inner')
    del df['x60']
    
    df = pd.concat([df, run_encode(df, 'x65')], axis=1, join='inner')
    del df['x65']
    
    df = pd.concat([df, run_encode(df, 'x77')], axis=1, join='inner')
    del df['x77']
    
    df = pd.concat([df, run_encode(df, 'x93')], axis=1, join='inner')
    del df['x93']
    
    return df

def preprocessing(df, branch):
    
    # covert format 
    # (x7 and x19 can be numeric value which is transformed from the original value)
    f = lambda x: float(x.replace('%', ''))
    df['x7'] = df['x7'].apply(f)

    f = lambda x: float(x.replace('$', ''))
    df['x19'] = df['x19'].apply(f)

    # remove several columns because they have too many NAN
    # print(df.isnull().sum())
    del df['x30']
    del df['x44']
    del df['x57']
    
    del df['x16']
    del df['x49']
    del df['x52']
    del df['x54']
    del df['x55']
    del df['x74']
    del df['x78']
    del df['x89']
    del df['x95']
    
    # remove columns with only one value
    # (x39 and x99 only have one unique value)
    del df['x39']
    del df['x99']
    
    # deal with NULL value 
    # (There are different ways to deal with null values, such as imputation, drop and so on. 
    #  If drop is used, too few rows are left. So, I use imputation.)
    for col in df:
        if is_numeric_dtype(df[col]):
            #df[col].fillna(df[col].mean(), inplace=True )
            if branch==0:
                df[col] = df.groupby('y', sort=False)[col].apply(lambda x: x.fillna(x.mean()))
            else:
                df[col].fillna(df[col].mean(), inplace=True )
        else:
            #df[col].fillna(df[col].mode()[0], inplace=True )
            if branch==0:
                df[col] = df.groupby('y', sort=False)[col].apply(lambda x: x.fillna(x.mode().iloc[0]))
            else:
                df[col].fillna(df[col].mode()[0], inplace=True )

    # remove duplicate (no duplicate row found)
    # duplicate_df = df[df.duplicated()]
    # print(duplicate_df)

    # remove outlier for training data
    if branch==0:
        df = stats_lib.remove_outliers(df)
        #Q1 = df.quantile(0.05)
        #Q3 = df.quantile(0.95)
        #IQR = Q3 - Q1
        #print(IQR)
        #df = df[~((df<(Q1-1.5*IQR))|(df>(Q3+1.5*IQR))).any(axis=1)]
        #print(df.shape)
        
        stats_lib.show_correlation(df)
        # find relation between the variables
        #plt.figure()
        #c=df.corr()
        #sns.heatmap(c,cmap="YlGnBu_r",annot=True)
        #print(c.where(c>0.7))
        
    # encode categorical variables to numeric variables
    df = encode(df)
    #print(df.shape)

    return df
  
def get_model_lr():
    model_lr = LogisticRegression()
    return model_lr
        
def run_logistic_regression(x_train,x_test,y_train,y_test,fstr):
    
    # fit
    model_lr = get_model_lr()
    exec("model_lr.__init__(" + fstr + ")")
    print(model_lr)
    model_lr.fit(x_train,y_train)

    # evaluate
    p_pred = model_lr.predict_proba(x_test)[::,1]
    y_pred = model_lr.predict(x_test)
    stats_lib.validate_model(y_test, y_pred, p_pred)
    return model_lr
    

def get_model_rf():
    model_rf = RandomForestClassifier()
    return model_rf

def run_random_forest(x_train,x_test,y_train,y_test, fstr):
    
    # fit
    # help(RandomForestClassifier)
    model_rf = get_model_rf()
    exec("model_rf.__init__(" + fstr + ")")
    print(model_rf)
    model_rf.fit(x_train,y_train)

    # evaluate
    p_pred = model_rf.predict_proba(x_test)[::,1]
    y_pred = model_rf.predict(x_test)
    stats_lib.validate_model(y_test, y_pred, p_pred)    
    return model_rf

def save_file(x, file):
    np.savetxt(file, x, fmt='%f')
        
def train_Logistic_Regression(x, y, x_test_future,column_names):
    # sampling
    x, y = stats_lib.sample_unbalanced_data(x, y)

    # normalize data
    x = stats_lib.normalize_data(x)
    x_test_future = stats_lib.normalize_data(x_test_future)

    # dimension reduction through PCA
    x_reduced = stats_lib.reduce_dimension(x)
    x_test_future_reduced = stats_lib.reduce_dimension(x_test_future)

    #split the dataset into training (70%) and validating (30%) sets
    x_train,x_test,y_train,y_test = train_test_split(x_reduced,y,test_size=0.3,random_state=0)

    # choose the best model
    stats_lib.choose_best_model(x_train, x_test, y_train, y_test)

    # tune hyper parameters
    model = get_model_lr()
    para_grid = {
        'C':[0.1,1,10]
    }
    stats_lib.hyper_parameters_turning(x_reduced, y, model, para_grid)

    # tune hyper parameters
    # model_lr = get_model_lr()
    # paras_list = """penalty:'elasticnet', C:1.0:10.0:1.0, solver:'saga', l1_ratio:0.1:0.5:0.1"""        
    
    # fstr, score, results = tuning.tune_hyper_parameters(model_lr, paras_list, x_reduced, y)
    # print('----------------- tuning results of LG---------------')
    # print(fstr)
    # print(score)
    # print(results)

    fstr = fstr + "," + "max_iter=2000"

    # train using logistic regression
    print('------------Logistic regression---------')
    model_lr = run_logistic_regression(x_train,x_test,y_train,y_test, fstr)

    # predict and save
    file = 'logistic_regression_results.csv'
    x = model_lr.predict_proba(x_test_future_reduced)[::,1]
    save_file(x, file)

def train_Random_Forest(x, y, x_test_future,column_names):
    # sampling
    x, y = stats_lib.sample_unbalanced_data(x, y)

    # normalize data
    x = stats_lib.normalize_data(x)
    x_test_future = stats_lib.normalize_data(x_test_future)

    #split the dataset into training (70%) and validating (30%) sets
    x_train,x_test,y_train,y_test = train_test_split(x,y,test_size=0.3,random_state=0)

    # choose the best model
    stats_lib.choose_best_model(x_train, x_test, y_train, y_test)

    # tune hyper parameters
    model = get_model_rf()
    para_grid = {
        'n_estimators':[10,50,100]
    }
    stats_lib.hyper_parameters_turning(x, y, model, para_grid)


    # tune hyper parameters
    # model_rf = get_model_rf()
    # paras_list = """n_estimators:100:500:100"""        
    
    # fstr, score, results = parameter_tuning.tune_hyper_parameters(model_rf, paras_list, x, y)
    # print('----------------- tuning results of RF---------------')
    # print(fstr)
    # print(score)
    # print(results)

    fstr = "n_estimators=100"

    # train using random forest
    print('------------Random Forest---------------')
    model_rf = run_random_forest(x_train,x_test,y_train,y_test, fstr)

    # predict and save
    file = 'random_forest_results.csv'
    x = model_rf.predict_proba(x_test_future)[::,1]
    save_file(x, file)

def main():   
    # configure to show all information
    pd.set_option('display.max_rows', None)
    pd.set_option('display.max_columns', None)
    np.set_printoptions(threshold=np.inf)

    # close warning
    warnings.simplefilter('ignore')

    # read training data
    # (40000, 101)
    df_train = pd.read_csv('exercise_40_train.csv')
    # (10000, 101)
    df_test = pd.read_csv('exercise_40_test.csv')

    # preprocessing on both train data and test data
    df_train = preprocessing(df_train, 0)
    df_test = preprocessing(df_test, 1)

    # convert pandas to numpy
    df_train = df_train.to_numpy()
    x = df_train[:,1:]
    y = df_train[:,0]
    x_test_future = df_test.to_numpy()

    # Train
    train_Logistic_Regression(x, y, x_test_future, df_test.columns)
    train_Random_Forest(x, y, x_test_future,df_test.columns)

if __name__ == '__main__':
    main()


