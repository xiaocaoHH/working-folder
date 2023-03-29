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

from sklearn.pipeline import make_pipeline
from sklearn.preprocessing import StandardScaler
from sklearn.model_selection import cross_val_score

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
            df[col].fillna(df[col].mean(), inplace=True )
        else:
            df[col].fillna(df[col].mode()[0], inplace=True )    


    # remove duplicate (no duplicate row found)
    # duplicate_df = df[df.duplicated()]
    # print(duplicate_df)

    # remove outlier for training data
    if branch==0:
        
        Q1 = df.quantile(0.05)
        Q3 = df.quantile(0.95)
        IQR = Q3 - Q1
        #print(IQR)
        df = df[~((df<(Q1-1.5*IQR))|(df>(Q3+1.5*IQR))).any(axis=1)]
        #print(df.shape)
        
        # find relation between the variables
        #plt.figure()
        #c=df.corr()
        #sns.heatmap(c,cmap="YlGnBu_r",annot=True)
        #print(c.where(c>0.7))
        
    # encode categorical variables to numeric variables
    df = encode(df)
    #print(df.shape)

    return df

def evaluate(model, x_test, y_test):
    
    # evaluate
    p_pred = model.predict_proba(x_test)[::,1]
    y_pred = model.predict(x_test)
    
    score = model.score(x_test, y_test)    
    print("Score: " + str(score))
    
    conf_m = confusion_matrix(y_test, y_pred)
    print("Confusion matrix:")
    print(conf_m)
    
    print("Classification_report:")
    print(classification_report(y_test, y_pred))

    #calculate AUC of model
    auc = metrics.roc_auc_score(y_test, p_pred)
    print("AUC: " + str(auc))
    
def get_model_lr():
    model_lr = LogisticRegression(penalty='elasticnet', dual=False, tol=0.0001, C=10.0, fit_intercept=True, intercept_scaling=1, 
            class_weight=None, random_state=None, solver='saga', max_iter=500, multi_class='auto', 
            verbose=0, warm_start=False, n_jobs=None, l1_ratio=0.5)
    return model_lr
        
def run_logistic_regression(x_train,x_test,y_train,y_test):
    
    # fit
    model_lr = get_model_lr()
    model_lr.fit(x_train,y_train)

    # evaluate
    evaluate(model_lr, x_test, y_test)
    return model_lr
    

def get_model_rf():
    model_rf = RandomForestClassifier(n_estimators=500, criterion='gini', max_depth=None, min_samples_split=2, 
                                   min_samples_leaf=1, min_weight_fraction_leaf=0.0, max_features='auto', max_leaf_nodes=None, 
                                   min_impurity_decrease=0.0, min_impurity_split=None, bootstrap=True, oob_score=False, 
                                   n_jobs=None, random_state=None, verbose=0, warm_start=False, class_weight=None, 
                                   ccp_alpha=0.0, max_samples=None)
    return model_rf
def run_random_forest(x_train,x_test,y_train,y_test):
    
    # fit
    # help(RandomForestClassifier)
    model_rf = get_model_rf()
    model_rf.fit(x_train,y_train)

    # evaluate
    evaluate(model_rf, x_test, y_test)    
    return model_rf
    
def reduce_dimension(x):
    pca = decomposition.PCA(n_components=50)
    pca.fit(x)
    # print(sum(pca.explained_variance_ratio_))
    x_reduced = pca.transform(x)
    return x_reduced

def save_file(x, file):
    np.savetxt(file, x, fmt='%f')
        
def cross_validation(model, X_train, y_train):
    pipeline = make_pipeline(StandardScaler(), model)
    scores = cross_val_score(pipeline, X=X_train, y=y_train, cv=10, n_jobs=1) 
    print('Cross Validation accuracy scores: %s' % scores)
    print('Cross Validation accuracy: %.3f +/- %.3f' % (np.mean(scores),np.std(scores)))
    
def run_cross_validation(x, y, model):    
    cross_validation(model, x, y)


def train_Logistic_Regression(x, y, x_test_future):
    # dimension reduction through PCA
    x_reduced = reduce_dimension(x)
    x_test_future_reduced = reduce_dimension(x_test_future)
    
    # cross validation
    # print("------------Cross validation results for Logistic Regression---------------")
    # model_lr = get_model_lr()
    # run_cross_validation(x_reduced, y, model_lr)

    #split the dataset into training (70%) and validating (30%) sets
    x_train,x_test,y_train,y_test = train_test_split(x_reduced,y,test_size=0.3,random_state=0)

    # train using logistic regression
    print('------------Logistic regression---------')
    model_lr = run_logistic_regression(x_train,x_test,y_train,y_test)
    # predict and save
    file = 'logistic_regression_results.csv'
    x = model_lr.predict_proba(x_test_future_reduced)[::,1]
    save_file(x, file)

def train_Random_Forest(x, y, x_test_future):
    # cross validation
    # print("------------Cross validation results for random forest---------------")
    # model_rf = get_model_rf()
    # run_cross_validation(x, y, model_rf)

    #split the dataset into training (70%) and validating (30%) sets
    x_train,x_test,y_train,y_test = train_test_split(x,y,test_size=0.3,random_state=0)

    # train using random forest
    print('------------Random Forest---------------')
    model_rf = run_random_forest(x_train,x_test,y_train,y_test)
    # predict and save
    file = 'random_forest_results.csv'
    x = model_rf.predict_proba(x_test_future)[::,1]
    save_file(x, file)
    
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
train_Logistic_Regression(x, y, x_test_future)
train_Random_Forest(x, y, x_test_future)




